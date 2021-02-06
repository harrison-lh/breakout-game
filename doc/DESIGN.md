# DESIGN Document for Breakout
## Harrison Huang


## Role(s)
Excepting code used from the JavaFX Game tutorials to create an initial game loop, 
I wrote all the code for this project. This required both tinkering with the
front-end design of the game and the back-end functionality.


## Design Goals
- Create a functioning Breakout game, with components of paddles, balls, and bricks
- Implement good ball-bouncing behavior
- Add functionality for power-ups and other additional features

## High-Level Design
- Three higher-level frameworks helped to define the structure:
  - The GameWorld ran the game loop and was the game engine.
  - Each element on the screen was a Sprite.
  - The SpriteManager dealt with determining the state of all the Sprites.
  
- Each of the Ball, Paddle, and Block classes were implementations of the
  abstract Sprite class.
  
- Other features built off of Sprite or existing classes, like
  ExplodingBlock as a special type of Block.

## Assumptions or Simplifications
- The Paddle motion was determined by key presses, which means that its motion
  is relatively choppy, jumping a bunch of pixels for every key press rather
  than having smooth movement.
  
- In some cases, the ball essentially picks a random direction for which to
  bounce after having hit a block or paddle, which is fine for the most part
  but sometimes results in unexpected behavior.
  
- I used the pixel count and ratio to determine where the paddle seemed to 
  "round" off, which is precise enough but would more properly be generalized.
  Some size ratios are also always scaled to their original dimensions, but they
  would be better if they were generalized.
  
- I didn't have a splash screen, though I did try to implement this but to little
  success. My button to begin the game wasn't stopping the game loop from running,
  so I ended up scrapping that.


## Changes from the Plan
- I didn't end up creating the levels as I drew out in my plan, which was a bit 
  of a consequence of me not figuring out how to import from a data file.
  Similarly, I didn't have a Level class, which probably would've been helpful
  in conjunction with data reading.

- I missed two power-ups that I wanted to implement: having large balls burst into
  many smaller ones and slowing time down. I wanted new balls to be able to come
  from the current one, but I ended up just having new balls spawn in like the
  initial one.
  
- My initial idea of having the balls grow in size over time also wasn't really 
  implemented in as successful of a manner as I wished, but I think this problem 
  would be more attributed to a lack of play-testing and tinkering with the 
  specific game mechanics.

## How to Add New Features
- The large balls that burst into multiple smaller balls would probably be done 
  by creating duplicate instances of the same ball, but just as additional objects.
  The direction of velocity could also be altered with the method to rotate motion.
  
- Slowing time could probably be done by altering a global speed variable, slowing
  down the movement of balls and power-ups by a factor for a certain period of time.
  
- Different types of blocks (falling blocks, moving blocks) could probably be 
  implemented by extending the existing Block class, adding additional functionality
  in either the update method or doing something different upon death.