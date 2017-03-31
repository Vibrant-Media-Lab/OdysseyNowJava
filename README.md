# ODSYredux

A group project by students and faculty at the University of Pittsburgh.

The game is meant to be played with our analog controllers, however, keyboard controls are also available.

Eventually, the game will be packaged into an executable, but for we just have java files that require a little extra work to run.

# Controls
  Player 1
  
  WASD controls movement
  
  Adjust Ball English with 2 and 3
  
  Player 2
  
  Arrow keys control movement
  
  Adjust Ball English with . and /
  
  Exit the game with ESCAPE
  
# Weird Bug
  For whatever reason, the WASD controls get sticky. Basically, if you use them for 5-10 seconds or so, they will suddenly stop, while the arrow key controls never have this issue. I have not yet figured out why this happened, but I do have a temp fix that seems to work! If your WASD controls stop working, hold down Command (on macOS) or Control (on other OS's) and press S to go down; this seems to unstick the controls!

# How to run a java file
  If you're not familiar with programming, this might be a little jarring, but I'll try to be as user friendly as possible!
  Here's a step by step guide on how to run these files:
  1. Download this whole repository to your computer. You do this with the green button at the top right-hand corner. You'll have to unzip the folder if you download it as a zip.
  2. Open up terminal (if you're on mac) or command prompt (if you're on windows). If you're on linux, I'm assuming you don't need this guide and already know what you're doing.
  3. You need to navigate to the folder in terminal/command prompt. You use the "cd" command to do this. So, for example, if your folder is called "ODSYredux" and it's currently in "Documents", type the commend "cd Documents/ODSYredux" and press return/enter. If you're ever confused, you can always use the command "ls" to show the contents of your current directory, or use the command "cd .." to go back to the parent directory of wherever you are. 
  4. Once you're in the correct folder, you'll need to compile the java files. Do this with the command "javac ODSYredux.java". If you run into any errors, this means you probably don't have updated java drivers. Sorry if that happens; I'm not going to write a guide extensive enough to cover that, sorry! 
  5. Now that the java files are compiled, it's ready to run! Just use the command "java ODSYredux"

