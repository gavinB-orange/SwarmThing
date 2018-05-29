# Design

## Introduction

SwarmThing is intended as a simulation game where the user sets up initial conditions, and then
lets the simulation run. The results of the simulation are (optionally) analysed against a list
of known challenges, generating a score.

## Configuration

A large part of the game is in setting the configuration. You can set the configuration of each of
the two types of beast (FoodBeast and GrazingBeast) and also for the simulation as a whole.
A separate screen is shown for each of the 3 configuration cases. Parameters are mainly configured
using SeekBars rather than the user being able to enter exact numbers.

All configuration information is stored in the default SharedPreference, from where it is extracted
during the simulation and results display.

## Overall architecture

The game is made up of a series of Activities. Simple UIs are provided to configure a number
of parameters, and a SharedPreference and Intent putExtra / getExtra are used for most of the
communication. The main simulation stage itself is a separate activity.

## Results display

The results are shown via :

1. Dump of the parameters used.
1. Challenge result.
1. Graph showing the evolution of overall beast numbers and food beast numbers, and also the
   birth and death rates (scaled to make a bit more visible).

The graph is generated as an offline bitmap, then inserted into an ImageView in the final
EndActivity. The bitmap is temporarily stored as a local file to permit passing a relatively
big image without causing problems. The data is too large to be passed as an intent.

## Recording results

During the simulation, every so often (about 50 frames worth ~= 1 second) a data sample is recorded
using a specific Recorder class which in turn makes use of DataItem as the underlying tuple.
The Recorder also contains the methods that generate the graph bitmap.

## Challenges

Challenges are implemented as classes that extend ChallengeChecker, together with a
ChallengeActivity class that is used to select the challenge, and contains a list of the known
challenges and their description. ChallengeChecker is an abstract class, and descendants must
implement the validate() method which takes the Recorder instance as an argument, permitting
access to the data gather during the simulation. Also, a getPoints() method must be implemented.
The results of the validation are passed via an Intent "extra" to the EndActivity where they
are displayed.

## The main simulation

When the simulation is run, a classic game loop is executed that takes the entire screen.
The screen orientation is currently fixed to portrait as changing orientation would require
save/restore and modification of how the simulated beasts are displayed.

A SurfaceView is used to get the canvas for drawing, and the items in the simulation are
displayed along with a head-up-display (HUD).

In the simulation configuration, you can set :

Grazing Beast / Food Beast ratio : this controls how the initial population is distributed.

Beast count : How many beasts you start with.

Simulation time : How long to run the simulation for. There is also a switch to make this time
unlimited.


## Sound effects

Simple sound effects can be enabled. They are disabled by default.

## HUD

A simple HUD is drawn during the main simulation run which can show basic data such as elapsed
time, remaining time, number of beasts, and the FPS rate.

## FoodBeast

The FoodBeast gets a share of the overall amount of energy that is added on each loop.  The amount
of energy a beast has is reflected in the image drawn.

Initial energy : how much energy the beast has to start with, which is also the maximum it can hold.
    Energy is lost or gained per turn, and an energy of 0 causes the beast to die.
Split threshold : the lower this value, the more likely the beast will split into two. Beasts
    split based on a healthy energy situation.
Max age : the maximum age before the beast dies.
Max light energy per beast : The maximum amount of energy the beast can take. The overall amount
energy being added is shared out across the available beasts, but if there are only a small
number of beasts, the amount per beast can become considerable. This limits that effect to a
sane maximum value.

## GrazingBeast
The GrazingBeast does not get energy directly, but needs to take from a FoodBeast. Each time it
bumps into a FoodBeast it will take a portion of energy from it. The amount of energy a beast has
is reflected in the image drawn.

Initial energy : how much energy the beast has to start with, which is also the maximum it can hold.
    Energy is lost or gained per turn, and an energy of 0 causes the beast to die.
Split threshold : the lower this value, the more likely the beast will split into two. Beasts
    split based on a healthy energy situation.
Max age : the maximum age before the beast dies.
