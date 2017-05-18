# FNSR-Project Description
(Frans Nyberg, Salim Rabat)

## How to play

Download and guide is at the git wiki: [How to start playing](https://github.com/salimrabat/FNSR-Project/wiki/How-to-start-playing).

Screenshot: [Random Rhythm](https://github.com/salimrabat/FNSR-Project/wiki/Random-Rhythm-preview). When notes reach the "target notes" on the bottom of the screen, hit A, S, D or F respectively. The game ends after a fixed number of notes are caught, or missed.

## What is this game?

Game name: Random Rhythm.

Why the name Random Rhythm? Our idea is to develop a rhythm game but unlike popular rhythm games, our beats are spawned on random columns. This is also a reason we have no music. It gets quite complicated if randomness should suit music.

Our current game has randomly generated inputs, a feedback signal for each correct hit and a score counter.

It is thought that if this game is developed further, open-source music (of course), graphics and more special effects are on the board.

## Frameworks

We're using the LibGDX game engine, which uses a game library for Java LWJGL. Although it has a lot more than our project needs, it is one of the safer choices for Java game engines since it is well maintained and up-to-date.

## Test strategy

A simple division of test types for a game are what I choose to call scripted tests and manual tests. Scripted ones launch a different game instance using test-specific parameters which run themselves. Assertion is mostly made by looking at the visual outcome, but also, when feasible, assertion clauses are used for hardly visible methods. Manual tests is simply playing the game, looking for things which tests don't cover and for possible things missing in the game.

So far however, only the rhythms and falling animations are tested in a scripted way. So if more development is made, there should definitely be more scripted tests for the other things such as input, score count and main menu - game screen connections.
