# FNSR-Project Description
(Frans Nyberg, Salim Rabat)

## What we are making

Game name: Random Rhythm.

The main purpose is to design a very simple yet somewhat appealing game. Our idea is to develop a rhythm game but with random beats instead of ready-made courses. Despite the genre being called rhythm, we realize that music is an optional layer of complexity and it depends how much we manage to get done in time. Our current game has randomly generated inputs, a feedback signal for each correct hit and a score counter. Screenshot: [Random Rhythm](https://github.com/salimrabat/FNSR-Project/wiki/Random-Rhythm-preview).

Expanding beyond this, further ideas are to have open-source music and graphics and more special effects.

## How to play

The plan is simply to include all relevant files in its own downloadable repository, launch the game from an executable file and play the game in its own window.

Any possible further options are planned to be implemented within the user interface of the game itself and not by just changing files manually. However, as an exception, if the project is expanded to include integration with music of your own choice, it may be possible to add your own music in a folder dedicated to only music.

## Frameworks

We're using the LibGDX game engine, which uses a game library for Java LWJGL. Although it has a lot more than our project needs, it is one of the safer choices for Java game engines since it is well maintained and up-to-date.

## Test strategy

A simple division of test types for a game are what I choose to call scripted tests and manual tests. Scripted ones launch a different game instance using test-specific parameters which run themselves. Assertion is mostly made by visually looking at the visual outcome, but also, when feasible, assertion clauses are used for hardly visible methods. Manual tests is simply playing the game, looking for things which tests don't cover and for possible things missing in the game.
