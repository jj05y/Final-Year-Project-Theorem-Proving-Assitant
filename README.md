# Theorem Proving Assistant

A graphical software package for proving theorems in the style developed by Djysktra.
This is a work in progress, at times it may be buggy. It is not proven 100% correct, any feed back is welcomed.

A sample set of theorems are pre-loaded into the theorem proving assistant. Further theorems are available in ./TheoremFiles/

You can also type new theorems in, click start, and then click keep.

## Run Instructions
- run:
  - java -jar TheoremProvingAssistant<High/Low>DPI.jar

## First Proof instructions
- Enter a starting theorem. Example formats:
  - X and Y
  - X and ! ( Y or Z )
  - X -> ( A or B )
  - X == Y and Z
  - X <= Y == Y => X
  - <| forall i : r.i : f.i |>
  - x down y = y down x
  - n = |_ n _|

- Click start
- Select a sub expression by clicking the expression where you want
- Choose a theorem to apply
- Choose replacement option from provided list
- Continue selecting and applying until satisfied.
- Click Keeper to keep the proven theorem

## Derivation of saved Theorems
- After keeping a theorem click on it (with no subexpression selection made) to view its history

## Loading and saving
- Create theorem files with a new theorem on each line in the above format
- Saved theorems will be in a json format but you can add to this list with the above format

## Screenshot
- All theorems to carry out this demo proof are provided in the default pre-loaded set.
#### Proof that X or true is the same as true ( X v true == true ).
![Alt text](/screenshot.png?raw=true "X v true == true")

