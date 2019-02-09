# Project: NAME OF PROJECT
## Student(s):  Siri Khalsa

## Introduction
This is a basic simple Domino game. there is a Text based version and a GUI based version.

## Contributions
There is only one person in this project.

## Usage
####TEXT-version1 
For the text based version you will see your hand and the current state of the play board.
The hand  will also print the index of each domino. You will use this when you make a move.
The moves can consist of the following:

[draw]
or
[index] [top|bottom] [left|right] [rotate (optional)]
EXAMPLE: 5 top left rotate

if you need to draw a domino from the boneyard type in draw and press enter. if you want to play 
the the domino with index 2 and play it on the top row on the right side of the board you would type:

2 top right 

if you want to rotate the piece before you play it then just add rotate to the end of the command before you press enter:

2 top right rotate
 
####GUI-version2

This version is literally built off the same code as the text version. You will notice it renders pretty similarly
 to the text based version. It gets more complicated when we start creating a good UI that accepts intuitive controls. 
 Under the hood the gui sends the same String[], [index] [top|bottom] [left|right] [rotate (optional)] , array back to the DominoGame object.
 
 For the first turn pick a Domino it will automatically get played for you.
 
 After this  you make a series of clicks on the screen that builds this string[] behind the scenes.
so to pick an index you click on a piece you want to play. to pick top left you will click the top left portion of the board play area.
If you click the screen where you want to play your piece it will convert it to the right row and side for you. If the piece should be rotated 
180 degrees before playing click the rotate button. when done press the finish button to play piece.

Quickly you can: click the piece, click the spot on the board you want to play, press finish to play without rotating or press rotate before you press finsih. 


## Project Assumptions
This project uses JDK 10 (Oracle version)

## Versions 

###Text based version
this jar is located in the root of the project. the name is: cs351project2-Dominos_Text_skhalsa10.jar
please see usage above for how to play the game. to run the game please type:

###### java -jar cs351project2-Dominos_Text_skhalsa10.jar

### V2
This jar is located in the root of the project folder. the name is: cs351project2_Dominos_JFX_skhalsa10.jar
please see usage above for how to play the gui game. to run this game please type:
 
###### java -jar cs351project2_Dominos_JFX_skhalsa10.jar

## Docs
/Doc/

## Status
### Implemented Features
this game both versions will allow you to draw from the boneyard even if you have a move. 

TEXT version - is fully functional as far as I am concerned. 

GUI version - this is fully fuctional version of the basic Domino game with a gui inteface 

### Known Issues

TEXT version - no none issues as far as I am concerned currently. I am sure there are bugs somewhere though.
GUI version - This system does not really control the input very well it will allow you to submit bad turns which are handled through exceptions 
exceptions so the game doesnt crash and it keeps working... but still.

BOTH VERSIONS - the logic allows a draw from the boneyard even if there is a move that can be played. this allows the player to draw the entire boneyard.
the chances are that the computer will always lose as they will run out of pieces first or need to draw first. and thus draw from an empty boneyard.

## Testing and Debugging
If you have tests, then explain how they work and how to use them.