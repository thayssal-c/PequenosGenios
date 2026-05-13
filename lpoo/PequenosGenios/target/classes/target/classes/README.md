The project itself is about an General Knowledges game structured with the POLI-UPE students OOP skills in Java. 

The mainly goal is to build a functional game with a defined system of battles between the created Characters.

Therefore, the scripts are divided in different folders, each one contains the functionality for the project. Alongside this, obviously respecting the OOP rules, the scripts are divides in such Class archives.

There are two types of questions: Multiple Choices and True/False questions.

Battle System Dynamics: Each round consists of: presenting a question, receiving the player's input, evaluating the answer, and applying the immediate effect (damage, healing, or skill activation) and the game ends when either the player or the opponent reaches zero health points (HP).

Chracters Attributes: Attack, Defense and Special Skills.

Abstract Classes: Character and Question.

Polymorphism: The game processes different types of questions (Multiple Choice and True/False) through a single reference of the abstract class Question, allowing the BattleManager to evaluate answers dynamically.

Interfaces: Implementation of the SpecialAbility interface to standardize how different power-ups (like ShieldAbility or DoubleDamage) interact with the characters during combat.

Encapsulation: The atributes (like Health and Attack) are private and accessed by Methods.

Link for the ClassesDiagram: 
