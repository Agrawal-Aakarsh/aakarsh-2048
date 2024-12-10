=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays. I used 2D Arrays to represent the game board primarily storing values for each
  cell. This is appropriate because I don't need to scale the game board within an instance of the
  game plus I need to represent a 2D game board which is most effective with a 2-dimensional data
  structure. Hence 2D arrays make sense to use.

  2. Collections. I have previous experience using Stacks in Java so I used a stack to implement
  the undo feature. I would pass each game state into the stack and given the LIFO functionality
  I could then continuously pop the previous board movement using the stack. This functionality is
  extremely useful because I don't have to constantly loop through an ArrayList to get the last game
  state (because if I used an ArrayList, to add a new element it has to loop through the entire list
  creating linear time complexity). And using an array is just not feasible because we cannot know
  how many moves a user is going to make and arrays are fixed size in Java.

  3. File I/O. I used File I/O to save the game when the user presses the back button so that even
  when the user exits the game, they can still load the last game and continue where they left off.
  Storing the game to a file makes the most sense because it's the least complex way to save game
  data because there's easy read and write functions to get/set the data.

  4. JUnit Testing. I used individual method testing as well as testing multiple methods together
  to validate my code runs through all the possible edge cases + common cases. In this process of
  testing I was able to catch a couple of bugs which I was then able to fix. (Note that I also did
  console testing/user testing (the main methods in each views) in order to test user experience
  as I built the game out).

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
   Once the game is launched, the RunAakarsh2048 class loads TitlePageView
   which is the class containing 3 buttons allowing the user to 1) create a new 2048 game, 2)
   load the previous game or 3) view instructions on how to play my game. We use action listeners on
   the buttons to invoke the respective views (GameView or InstructionsView).
   InstructionView contains a scrollview containing the instructions followed by a back button to
   allow navigation back to the title screen. The GameView is a little bit more complicated. It
   contains a GridLayout to represent the game board itself (size scalable since the constructor
   requires the dimensions), a score tracking label and a back button.
   GameView is constantly updated by the GameController which is the glue between Game2048 and
   GameView class (enabling abstraction within the greater MVC model). GameController is in charge
   of both updating the internal state of the game (done through the Game2048 class) and updating
   the graphical experience by calling the correct methods in GameView. It also saves the current
   game state to the file and loads the title page when the user presses the back button during the
   game. Game2048 is the core functional class. It maintains a stack that stores game moves to
   enable undo. The core logic of the class is in the move functions & "mergeAndSlide". The move
   functions move the values of the board to the direction of the movement & update the score.
   Furthermore we load the game and save the game as a 2 dimensional list on a text file followed
   by a number denoting the score like the following:
   ""
   2 0 0 2
   0 0 0 0
   0 0 0 0
   4 0 0 4
   8
   ""

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
   The biggest struggle was the algorithm to update the board. It was a struggle trying to figure
   out how to merge and slide the board in all directions but abstraction did help make it easier.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  I think in general I have done a good job of separation of functionality. There is very limited
  redundant code in my project & furthermore I encapsulate the game states so that I can only update
  internal states by calling move functions.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

Background Photo: https://www.pexels.com/photo/close-up-photography-of-yellow-green-red-and-brown-plastic-cones-on-white-lined-surface-163064/

I also used ChatGPT to write the switch-case functionality for tile coloring. I am attaching the
input I fed it:
"I am trying to implement a 2048 game. I have a method (private Color getTileColor(int value)).
I want you to use switch to return a Color object representing a color progression from Yellow to
Red as the numbers get bigger".