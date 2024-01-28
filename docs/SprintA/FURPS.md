## Supplementary Specification (FURPS+)

## Functionality

| UC/US                           | Description                                                    |                   
|:--------------------------------|:---------------------------------------------------------------|
| Language                        | Allows the user to change the main language in the application |
| Message Delivering              | Send a message to confirm the scheduling of a vaccine          |
| Digital Vaccination Certificate | Request the digital vaccination certificate at any given time  |
| Password                        | Each user must be authenticated with a password                |
| Log-in                          | The user can log-in to the account previously created          |
| Generate Reports                | Generate reports in specific time periods                      |

## Usability 

_Evaluates the user interface. It has several subcategories, among them: error prevention; interface aesthetics and design; help and documentation; consistency and standards._   

* Help and documentation - The worst-case time complexity analysis of the algorithms should be properly documented in the user manual of the application.

## Reliability

_Refers to the integrity, compliance and interoperability of the software. The requirements to be considered are: frequency and severity of failure, possibility of recovery, possibility of prediction, accuracy, average time between failures._
(fill in here)

## Performance

_Evaluates the performance requirements of the software, namely: response time, start-up time, recovery time, memory consumption, CPU usage, load capacity and application availability._

_**Both of these topics fit into more than one of the performance requirements.**_
* The application will implement a bruteforce algorithm in order to determine the contiguous subsequence with maximum sum. It is required for the implemented algorithm to be analyzed in terms of its worst-case time complexity.
It will also be compared to a benchmark algorithm.
* In order to see asymptotic behavior, the computational complexity study must be complemented with the observation of the execution time of the algorithms for inputs of varied size.

## Supportability

_The supportability requirements gathers several characteristics, such as:_

* Testability - Every method, beside Input/Output operations methods,  will have a unit test using JUnit 5.
* Adaptability - The software application should also be conceived having in mind that it can be further commercialized to other companies and/or organizations and/or healthcare systems besides DGS.
* Maintainability - Due to the testing and the OOP that must be implemented in the application, the maintenance will be easier and much more organized.
* Compatibility -The application should be capable of managing other future pandemic events requiring a massive vaccination of the people.
* _Configurability_ - Alternate between languages.
* _Installability_ -.
* _Scalability_ -.

### Design Constraints

_Specifies or constraints the system design process. Examples may include: software process, mandatory standards/patterns, use of development tools, class library, etc._

* Mandatory standards/patterns - The application graphical interface is to be developed in JavaFX 11.

### Implementation Constraints

_Specifies or constraints the code or construction of a system such as:_

* Implementation languages - The application is going to be developed in Java using the IDE IntelliJ.
* Mandatory standards/patterns:
  * All the images/figures produced during the software development process should be recorded in SVG format;
  * Javadoc should be used to generate useful documentation for Java code;
  * Use CamelCase while coding;
  * Utilize JUnit5 to perform code tests;
  * JaCoCo plugin will be used to generate the coverage report;
  * The application graphical interface is to be developed in JavaFX 11;
  * Object serialization.
* Database integrity -.
* Resource limits -.
* Operating system -.

### Interface Constraints
_Specifies or constraints the features inherent to the interaction of the system being developed with other external systems._
(fill in here)

### Physical Constraints
_Specifies a limitation or physical requirement regarding the hardware used to house the system, as for example: material, shape, size or weight._
(fill in here)