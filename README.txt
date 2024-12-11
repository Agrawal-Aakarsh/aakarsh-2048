=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

=============================
=: Implementation Overview :=
=============================
   TLDR: Classic implementation of the 2048 game. 

   Expanded Architecture Explanation: 
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
     Furthermore, we load the game and save the game as a 2 dimensional list on a text file followed
     by a number denoting the score like the following:
     ""
     2 0 0 2
     0 0 0 0
     0 0 0 0
     4 0 0 4
     8
     ""


========================
=: External Resources :=
========================

Background Photo: https://www.pexels.com/photo/close-up-photography-of-yellow-green-red-and-brown-plastic-cones-on-white-lined-surface-163064/
