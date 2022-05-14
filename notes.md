## Rules

1. Captures are made forwards or backwards.
2. Captures are mandatory.
3. In the situation where you have the choice between making multi-jumps over your opponent’s
   checkers, it is mandatory to choose the one that make you capture the maximum pieces.

## TODO:

* Debug Menu
- Toggle square numeration

Capture 1 piece with man:
1. Check the surrounding pieces ("checked position"):
 - check top-left,
 - check top-right
 - check bottom-left
 - check bottom-right
2. If the "checked position" is of different color:
 - check if "checked position" + 1 is empty, if it is:
    - add "checked position" + 1 as valid move
    - change current position to "checked postion" + 1 and repeat the steps again

    
check([1:1])

fun check(currentPosition, path: List)



Game logic:

- Change turn: let the players move pieces in turns
- Capture 1 piece:
  - check if should capture 
  - capture
- Capture multiple pieces by man
- Promote to king
- Capture multiple pieces by king
- Detect the the winner
- Detect draw 
- ~~Implement move piece logic for my player~~
- ~~Use round shapes for pieces~~
- ~~Position the board center of the screen~~
- ~~Board size should be dynamic depending on the screen size~~
- ~~Rewrite `canPick` logic so that it traverses the diagonals and accounts for Man and Kings~~
- ~~Programmatically detect initial positions of pieces~~
- ~~Show valid positions for my player where a piece can move when clicking on it~~

## Features

- Start new game
- Abort game
- It should be able to play checkers with yourself
- It should have the possibility to customize the AI level:
    - Player 1: P1 knows how the rules of the games but he has to win a game yet
    - Player 2: P2 has won two times in the school championship ...
    - Player 3: P3 ...
- Customizable board
- Show lessons
- ~~Highlight squares when a move is made~~

## Buzz words

board, square, row, piece, man, king, light, dark, pick, place,  
player, opponent

## Resources

https://www.gamesver.com/the-rules-of-checkers-simply-explained-for-beginners/
https://lidraughts.org/  
https://lidraughts.org/variant/standard
https://lidraughts.org/variant/russian  
[https://cardgames.io/checkers/#rules](https://cardgames.io/checkers/#rules)  
[https://en.wikipedia.org/wiki/Checkers#National_and_regional_variants](https://en.wikipedia.org/wiki/Checkers#National_and_regional_variants)
