# PV-Dimension - Photovoltaic Systems Simulations
Built during 3rd semester at ABBTS - Software Engineering & Project Management Course

See the paper [here](./doc/Semesterarbeit.pdf) (in german)

## Disclaimer
This tool was created during my studies as a school project in 2015. **No guarantee is given for accuracy or completeness.**

## What is it?
It is a GUI application with which a PV system can be dimensioned.
The calculation is based on the existing roof area, the power consumption profile and the weather data.

<img width="1512" alt="image" src="https://user-images.githubusercontent.com/13404717/198888993-8578f0b3-ee71-4e16-af4c-204b1d42d211.png">

## Features
- configure solar panel amount, size, efficiency, angles
- solar panel type can be chosen from a database
- configure battery capacity
- determine solar irradiation by plant location or specify manually per day and time
- specify power consumption (e.g. of a household) for each day and time during a week
- simulate the whole system and see how it performs during specific days, weeks, years
- simulate economic efficiency by setting cost of power per kWh for buying and selling, cost of the plant (or its components)
- generate reports (csv, pdf)

## Todo/Known Issues

- Getting lat/long of an address via Google Maps needs an api key and does not work right now (anymore)
  - solutions: replace with a different api, allow lat/long to be entered manually, add possibility to add api key
