# Breakout Project Analysis
### Harrison Huang

## Status
- I spent most of my time on this project on two things:
  - Setting up the game engine from the start
  - Getting collisions for the ball and paddle/blocks to be as accurate as I 
    wished, along with their various behaviors like the ball bouncing differently 
    off the corner and if the paddle was in motion recently

- DRY/Duplication:
    - I'm not completely satisfied with the way I implemented the update() method 
      for each of the Sprite extensions, as some are utilized much better than 
      others. I believe the handleUpdate method could also be simplified to better
      utilize the abstract update() method.
      
    - I don't use the isDead boolean in the Sprite class very well, and there
      are inconsistencies with how I do use it. It is likely better to be deleted
      altogether, as I have other ways of removing Sprites given their need to
      be deleted.
      
    - My implementation to create new levels is also somewhat repetitive, but
      that problem is better solved by implementing level import from a data file.

###Methods: 
```java
private void ballCollidesPaddle(Ball ball, Paddle paddle)
```
This method handles what happens when a ball collides with a paddle, determining
which direction the ball should go after this collision. There are a lot of
moving parts to this method, as it relies on a few other similar methods to
determine how the ball will move. The methods are mostly self-explanatory,
though they are probably indistinguishable to most people. I think this is okay
in terms of the Single Responsibility Principle, as the method accomplishes
one (big) function of determining what happens to the ball in a collision
with the paddle. My commit messages are pretty sparse in addressing this method,
as I have vague messages regarding updating the collision behavior. The commits
I did were a mix of new code and debugging, as I was trying to make my collision
behavior much better and in a more expected manner.

```java
protected void handleUpdate(Sprite sprite)
```
This method updates every one of the Sprites, called once every frame of the game.
It does a lot of different things depending on what type the Sprite is, whether it
be a ball, paddle, laser, or something else. I feel like this method probably
violates the Single Responsibility Principle because it tries to do too many things
at once, "updating" each of many things. "Update" is simply too vague to describe
all of what this method does. This method is not described by GIT commit messages
anywhere, as it was one that was continuously updated over time, as opposed to being
written once and touched little thereafter. 

###Conclusion

I think I was moderately successful at making my code readable, though I likely
need even some amount of inline comments to clarify some lines. I have very little
comments throughout my code, so the amount to which my code could be understood
would rely on its structure, which is something I paid attention to as I was
finishing my project. There is a significant amount of methods I created, and
though it's imperfect, I feel okay about my refactoring, since I broke down
the methods that were really long into shorter understandable chunks.

## Design

- Levels are hard-coded into the game in the ```constuctLevel``` method, which
  is not a good solution in the long term. Each of the blocks is manually coded,
  though in a for loop to make it slightly less intensive. To add a new level,
  one would have to create each block manually or with a loop.
  
- To accomplish some of the methods like ```ballCollidesPaddle``` above, 
  the game class must depend on the Ball class to make correct decisions for it,
  so the Ball class has these strange public methods. I also don't like the fact
  that I rely on a lot of public variables when I should probably be using
  getters and setters in the same place.
  
- For the laser power-up feature, which destroys a whole column of blocks,
  I implemented it near the end, but not as the last thing I did. 
  It was after I had already implemented the paddle size and
  extra ball power-ups, so it was a natural progression to add another power-up.
  I thought the laser was something I could do rather easily without too much
  trouble. The laser depends on Sprite and BreakoutGame to work, though
  in practice it also depends on Paddle and PowerUp. I have a ```shootLaser()```
  method in my game class, which creates a new Laser power-up. It only gets
  released at the midpoint of the paddle, and it likely fails otherwise. In the
  case that the laser doesn't work, it likely wouldn't affect much of other
  functionality, and it's mostly independent of other classes.
  
- For the exploding block, it was actually the last thing I did (or tried to do).
  It was one last step I could take to extend the functionality of a normal block.
  It's intended to destroy surrounding blocks after it itself is destroyed. It
  requires the Block class, an image, and the main game class to work. My method
  of detecting whether a surrounding block should be deleted is making the 
  exploding block momentarily physically bigger to create collisions, which is
  probably not the most optimal way. Exploding Blocks were one of the last things
  I did, so it has little effect on other things, except for the fact that it
  isn't deleted properly, so it's still buggy in that sense. Its implementation
  changed in the fact that its inheritance was tricky how to work out, while still
  being a block but also having different features. I did add a method to get
  image in the Block class so that I could get an exclusive image for the exploding
  kind.
### Conclusion
I think the general hierarchy of objects is understandable and can interact 
reasonably, since each of the objects on screen is a type of Sprite, all
managed by the game and SpriteManager. It should be relatively easy to change
the characteristics of any one of these objects since they act mostly
independently of each other.

## Alternate Designs
- There were not many other paths I could have taken for design choices, but I
  think I did question whether or not, in some cases, to implement a new feature
  by creating a class that also extends Sprite or one that extends an already
  existing object. It's a bit unclear how closely related some elements are to
  each other, which can make it difficult to make a decision.
  

#### Telling the Other Guy
- I'm still unsatisfied with how much work my game class has to do in order to 
  determine the direction the ball will go next, since the ball could also
  itself determine where it wants to go next given some amount of circumstances.
  
### Design Decisions
- One design decision I made was to have PowerUp be its own class, but have it
  be created essentially exclusively by the Block class. A different Block
  constructor allowed for it to contain a power-up, which I thought was a pretty
  good decision. Since a power-up must wait until it is allowed to move, and then
  constantly move down, I think it was more optimal since a Block must always be
  still. A, say, PoweredBlock class extending Block could have also worked, but I
  think it was important to separate the Block from the PowerUp element.
  
- Another change that I was implementing was differently colored blocks. I first
  just had different blocks for different initial health values, but I extracted
  this into a method in order to allow ExplodingBlock to have its own image. I
  thought this was pretty clever, but it's a bit strange since the method for
  getting the image needs to be public, which it feels like it shouldn't be
  since it's only internal usage. The exploding block instead could have been
  an extra parameter to the existing Block class, but I think that would clutter
  the class.
  
I think the way I've designed my Breakout Game allows for easier extensions of
more general types of objects. I have a basic framework of 
Paddle/Ball/Block/PowerUp, so these things can be made different and features can
be added with such extensions.

## Personal

- The JavaFX tutorial was a major help to me getting a game engine/loop started. 
  I progressed by being able to learn patterns from source code and examples. It
  takes a lot of understanding to be able to extract patterns and apply them to a
  new situation, so I had to take a lot of time there.
  
- I think it'd be better for me personally not to spend bursts of work that are too
  long, since it's just exhausting and takes a lot of curiosity and wonder out of
  the project. I should also try to prioritize work, as I tend to want to jump
  around a lot.
  
- I have learned there's a lot of ambiguity around getting things right, and 
  there's more than one right way to do something. There's also a lot of hard
  work that goes on in design, working on different levels as well.
  
- To be a better designer:
    - Start doing differently - GIT commits and messages, prioritization of 
      different features
    - Keep doing the same - abstraction and inheritance, refactoring/extract methods
    - Stop doing - truly messy code

### Google Form Responses/Reflection

I definitely underestimated the size and scope of this project, though I was 
prepared to spend a lot of time working on this project regardless. I believe 
there were many more new elements to working with creating a fully functioning 
game than I had initially though, so I oversimplified it a bit in my head, while 
I still had to manage all these functions in code. At the same time, I probably 
didn't fully understand what I was getting into as I was starting, so it would 
be better for me to grasp concepts to a greater degree before programming.

In general, I spent much more time working up close to the deadline 
(Saturday-Sunday) than the week leading up, though I can't really fault myself 
too much for not working or starting earlier. I spent much time around 
Tuesday/Wednesday trying to set everything up, while I really only started to 
implement key features between my working on Wed/Sat. I was honestly surprised 
at how much I could implement on Sunday alone given how much (or little) I had 
done up until then. Quite a few of the nonessential but key aspects of the 
Breakout game were done very late. While I started working in smaller chunks 
of time, when the weekend rolled around, Breakout was my total focus, and the 
vast majority of my hours on Breakout were on Saturday/Sunday. In the future, 
I should hopefully have a more balanced schedule outside of 308, which would 
allow me to work on projects without binge-like sessions. Within 308 though, 
I think it'd be important for me to prioritize what I want to do or implement, 
as I jumped around quite a bit, especially near the end when I was hopping 
around the place trying to do a lot at once.

Once I knew what I was doing, it was definitely easiest to implement a new class 
which inherited already existing features, and manipulate this to how I want to 
implement a new feature into the game. Using Ball as a bit of a template, it was 
rather easy to make the Laser class. I think it was hardest to get started, as I 
had a lot of code through the game engine and my new game class, much of which I 
didn't understand. I had to rely heavily on tutorials and previous examples in 
order to do things as I wanted, so that made it difficult. However, I think 
spending time creating the framework with the sprites/manager/game loop was 
worth it, as many of the later features that I implemented were relatively easy 
as by that point, I was familiar with how the parts worked together, and the 
abstractions helped me to create similar enough objects. Abstractions and 
inheritance are really useful in making your life easier later on, so I think 
I'll have to keep that in mind as I move forward. I think I spent too long 
tinkering with the paddle mechanics when they're not necessarily the most 
essential to the project function. Again, prioritization would be really 
helpful here.

I think my usage of GIT is not too bad, but I could definitely commit more often. 
I do many things at once, so my commit messages likely miss a lot of the things 
I do during programming. Looking back and reading my commit messages, there is 
definitely a progression of what happened over the course of the project, but 
I'd like to see even more. I think I improved on this over time, as I implemented 
more concrete features, but I could still use more. I only recently learned that 
you could commit only specific files and commit without pushing. GIT should be 
more used as a continuous progression rather than simply as checkpoints of 
arbitrary stopping/saving.

The way I implemented collisions between the ball and paddle/blocks required by 
far the most editing. Since reflection of the ball's velocity was managed by the 
main breakout game class, I had to make sure the class had enough information in 
order to make decisions on this. I think my use of the Sprite class was really 
helpful in making my later features much easier, though I did have ugly blocks of 
code in my breakout game class, which was improved somewhat by refactoring. I 
have learned that it is really easy to get trapped into ugly code if you're not 
focused on keeping clean code but rather functional code. The refactoring tool 
is also infinitely useful for keeping code in smaller chunks.

My highest priority would likely be fixing the buggy ExplodingBlock implementation, 
as (1) the block is not properly deleted after exploding and (2) it doesn't explode 
all the surrounding blocks, just a single block other than itself.