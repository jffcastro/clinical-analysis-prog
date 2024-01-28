# Supplementary Specification (FURPS+)

## Functionality

_Specifies functionalities that:_

- _are common across several US/UC;_
- _are not related to US/UC, namely: Audit, Reporting and Security._


| Function   | Description   |
| ---- | ----------- | 
| **Authentication**    | **Every user must be authenticated with an email and a password** | 
| **Data's Security**   | **Only Nurses should be able to access user's health data** | 


## Usability 

_Evaluates the user interface. It has several subcategories,
among them: error prevention; interface aesthetics and design; help and
documentation; consistency and standards._   

* Javadoc should be used to generate useful documentation for Java code;


## Reliability

_Refers to the integrity, compliance and interoperability of the software. The requirements to be considered are: frequency and severity of failure, possibility of recovery, possibility of prediction, accuracy, average time between failures._


(fill in here)

## Performance

_Evaluates the performance requirements of the software, namely: response time, start-up time, recovery time, memory consumption, CPU usage, load capacity and application availability._


## Supportability
_The supportability requirements gathers several characteristics, such as:_

* Testability - Every method, beside Input/Output operations methods,  will have a unit test using JUnit 5;
* Adaptability - The software application should also  e conceived having in mind that it can be further commercialized to other companies and/or organizations and/or healthcare systems besides DGS;
* Maintainability - Due to the testing and the OOP that must be implemented in the application, the maintenance will be easier and much more organized;
* Compatibility -The application should be capable of managing other future pandemic events requiring a massive vaccination of the people;
* Configurability -
* Installability - 
* Scalability - 


## +

### Design Constraints

_Specifies or constraints the system design process. Examples may include: programming languages, software process, mandatory standards/patterns, use of development tools, class library, etc._

* The application is going to be developed in Java using the IDE IntelliJ.
* The application graphical interface is to be developed in JavaFX 11.

### Implementation Constraints

_Specifies or constraints the code or construction of a system such
such as:

* Implementation languages - The application will support portuguese and english;
* Mandatory standards/patterns - **Object serialization to ensure persistence**
* Database integrity -
* Resource limits -
* Operating system -



### Interface Constraints
_Specifies or constraints the features inherent to the interaction of the
system being developed with other external systems._


(fill in here)

### Physical Constraints

_Specifies a limitation or physical requirement regarding the hardware used to house the system, as for example: material, shape, size or weight._
_**Probably talk about the weight or some pc´s that are not able to handle the app.**_

(fill in here)