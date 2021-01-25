# Game Plan
### Harrison Huang

## Interesting Breakout Variants
I really like the Centipong variant, with (1) the concept of having a
massive number of balls exploding out after hitting a "block", and (2)
the power-up that slows down time that becomes more interesting combined 
with the large number of balls on screen. Otherwise, I think I also like
the Super Breakout, but mostly because of its implementation of a few
new features in a relatively minimalistic manner. While I like the chaotic
nature of Centipong, there is also something to say about the zen-like
gameplay of having a single ball (or just a few balls) and slowly
building up in Super Breakout.

## Paddle Ideas
1. Having the balls be influenced by the direction that the paddle is moving
   (i.e. incorporating momentum into the paddle-ball interaction).
   
2. Warping of the paddle between the left and right side of the screen.

## Block Ideas
1. Multiple-hit blocks

2. Certain blocks drop extra lives and/or power-ups

3. Blocks that explode when destroyed

## Power-up Ideas
1. Ball grows in size over time, which makes it easier to hit with the paddle

2. Large balls can burst into many smaller balls

3. One power-up will slow time, allowing the player the ability to hit 
   more small balls with the paddle in a short period of game time

4. A laser can clear a whole column of blocks

## Cheat Key Ideas
1. One key will increment the player's counter for remaining lives

2. Enables blocks to be destroyed if hit by a ball regardless of how 
   strong the block is
   
3. Immediately shoots a column laser from the paddle

4. Immediately bursts one active ball into many balls (likely 
   to be implemented with a cooldown so as to prevent spamming)


## Level Descriptions

####Level 1:
- Most basic, with standard wall similar to original game
- Blocks in the center need more hits to break
- Possibly add power-up at or near the center

`ooooooooooooooooOOOOOOOOOOOOOOOOoooooooooooooooo`
`ooooooooOOOOOOOO0000000000000000OOOOOOOOoooooooo`
`OOOOOOOOOOOOOOOO0000888*88800000OOOOOOOOOOOOOOOO`
`ooooooooOOOOOOOO0000000000000000OOOOOOOOoooooooo`
`ooooooooooooooooOOOOOOOOOOOOOOOOoooooooooooooooo`

####Level 2:
- Bigger than level 1, with wall and two channels to break through
- Blocks in the center and at the gates need more hits to break
- Power-ups past the gates, center explodes when destroyed

`ooooooooooooooooOOOOOOOOOOOOOOOOoooooooooooooooo`
`ooooooooOOOOOOOO0000000000000000OOOOOOOOoooooooo`
`OOOO.**.OOOOOOOO0000888888800000OOOOOOOO.**.OOOO`
`OOOO..........OO000088*^*8800000OO..........OOOO`
`OOOO....OOOOOOOO0000888888800000OOOOOOOO....OOOO`
`oooo....OOOOOOOO0000000000000000OOOOOOOO....oooo`
`ooo008800oooooooOOOOOOOOOOOOOOOOooooooo008800ooo`

####Level 3:
- Biggest level, with wall and explosives scattered within
- Blocks in front and center need more hits to break

`000000000000000000000000000000000000000000000000`
`0oooooooooooo*^*OOOOOOOOOOOOOOOO*^*oooooooooooo0`
`0oooooooOOOOOOOO0000000000000000OOOOOOOOooooooo0`
`0OOO.**.OOOOOOOO0000888888800000OOOOOOOO.**.OOO0`
`0OOOOOOOOOOOOOOO000088*^*8800000OOOOOOOOOOOOOOO0`
`0oooooooOOOOOOOO0000888888800000OOOOOOOOooooooo0`
`0OOOoooo*^*OOOOO0000000000000000OOOOO*^*ooooOOO0`
`0oooooooOOOOOOOO0000000000000000OOOOOOOOooooooo0`
`888888888888888888888888888888888888888888888888`
`.888...888...888...888....888...888...888...888.`


## Class Ideas

### Block
- stores information for each block (location, hits remaining)
- method for decrementing numHits, and determining if block still exists
- class extensions include bricks that are also power-ups or explosives

### Ball
- stores information for each ball in play (location, size, speed/direction)
- methods returning current speed

### Paddle
- stores information for player's paddle (movement/location)
- methods that change location of paddle based on player's keystrokes

### Game
- stores information about the game, sets up game
- determines interactions between objects
- needs to calculate player score
- method for stepping through game

### Level
- creates predetermined level with correctly set blocks
- method to return all newly created blocks