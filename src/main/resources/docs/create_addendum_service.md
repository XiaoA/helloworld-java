# Create Addendum Service

## Original service (slightly modified)
This service is responsible for creating addendums. It's located in the "C-Service" repo.

```ruby
frozen_string_literal: true

class CreateAddendumService
  class << self
    def call(dept_information, r_number, form_type, draft_id = nil)
      return Typh::Command::Success.new('No info present') unless dept_information && dept_information.respond_to?(:each)

      dept_information.each do |mi|
        addendum = Addendum.find_by(r_number: r_number, tracking_id: mi.symbolize_keys[:deptTrackingId])

        if addendum
          Logly.info('Addendum already created.', { r_number: r_number, form_type: form_type }, context: self)
          return Typh::Command::Failure.new('Addendum already created.')
        else
          Addendum.create!(
            data: mi.merge({ "draftId" => draft_id.to_s }).to_json,
            r_number: r_number,
            tracking_id: mi.symbolize_keys[:deptTrackingId] || SecureRandom.uuid
          )
                  .log_submit!
          Loggly.info('Addendum created.', { r_number: r_number, form_type: form_type }, context: self)
        end
      end
      Typh::Command::Success.new('Addendums newly created.')
    end
  end
end
```

## Summary of Service
This service, `CreateAddendumService`, is responsible for creating addendums based on provided department information. It checks if an addendum already exists for a given R-number and tracking ID, and if not, it creates a new addendum with the provided data, logs the creation, and returns a success or failure command.

## Java Equivalents

I started by looking up some of the equivalent Ruby concepts in Java. For example:

| Ruby                                            | Java                                                            | Comments                                                                                                                                                                                        |
|:------------------------------------------------|:----------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Addendum.findBy                                 | AddendumRepository.find_by                                      | I'll need a Addendum Repository                                                                                                                                                                 |
| Addendum.create!                                | addendumRepository.save                                         | Instantiate a new object, generate setters, and pass it to the repository to save.                                                                                                              |
| SecureRandom.uuid                               | UUID.randomUUID().toString()                                    | Built-in methods for generation UUIDs in each language.                                                                                                                                         |
| mi.merge(...}.to_json                           | objectMatter.writeValueAsString(...)                            | Java is strictly typed, so converting an object to JSON requires the Jackson library.                                                                                                           |
| if ...else...end                                | if (condition) { ... } else { ... }                             | Java's if-else statements are just like Javascript's.                                                                                                                                           |
| mi.symbolize_keys                               | mi.toMap()                                                      | Ruby's symbolize_keys is not directly available in Java, but can be achieved using a custom method or library.                                                                                  |
| `[:deptTrackingId]`                             | `mi.getDeptTrackingId()`                                        | **Symbols vs Getters:** Ruby uses symbols (like `:key`) to look up hash values. In Java, objects use explicit getter methods to fetch data.                                                     |
| `\|\|` (for default values) | `if (trackingId == null)` <br>or<br> `Objects.requireNonNullElse(val, fallback)` | **The OR Operator:** In Ruby, `\|\|` is commonly used for "null coalescing" (falling back to a default value). Java's `\|\|` is strictly for boolean logic, so you have to use an explicit `if` check or a utility method to handle fallbacks. |
| `Loggly.info(...)`                              | `loggly.info(...)`                                              | **Class Methods vs Instance Methods:** Rails calls logging directly on a global class constant. Java prefers injecting a `loggly` logger object instance into the class first, then calling it. |
| `{ r_number: r_number }`                        | `Map.of("r_number", rNumber)` <br>or<br> `new HashMap<>()`      | **Hashes vs Maps:** Ruby hashes can be written inline with braces. Java uses the `Map` interface, which is instantiated as a `HashMap` or created inline using `Map.of()`.                      |
| `deptId = null` (in method signature)           | **Method Overloading** <br>(Writing two versions of the method) | **Default Parameters:** Ruby lets you define default arguments in a method signature. Java doesn't support this, so you write an extra "overloaded" method that passes the default value along. |
| `draft_id.to_s`                                 | `draftId.toString()`                                            | **String Conversion:** Ruby uses `.to_s`; Java uses `.toString()`. Note the case change to camelCase (`draft_id` becomes `draftId`).                                                            |
| `self`                                          | `this`                                                          | **Context:** Ruby uses `self` to refer to the current object context; Java uses the keyword `this`.                                                                                             |
| Freeze / Constants (e.g., `MY_VAL = 10`.freeze) | `private final String myVal`                                    | **Immutability:** Ruby uses capitalized constants or `.freeze` to stop objects from changing. Java uses `final` to ensure a variable can only be assigned once.                                 |
| `Typh::Command::Success.new` | `new TyphCommand.Success()` <br>or<br> `TyphCommand.success()` | **Namespaces vs Packages/Nested Classes:** Ruby uses `::` to look up namespaced modules. Java uses dots (`.`) to reference packages or nested classes. It is also common in Java to use a static factory method like `TyphCommand.success()` instead of calling `new` directly. |
| `obj.respond_to?(:method)` | `obj instanceof MyClass` <br>or<br> `obj.getClass().getMethod("method")` | **Duck Typing vs Static Typing:** Ruby checks if an object responds to a method name at runtime. In Java, you typically check if an object is an instance of a specific interface or class using `instanceof` before calling its methods. |