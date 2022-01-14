# Ariray

| Branch       |  Status 	|
|:-:	       |:-:	        |
|  Master      | [![Build Status Master](https://travis-ci.com/cadox8/Ariray.svg?branch=master)](https://travis-ci.com/cadox8/Ariray)   |
| Development  | [![Build Status Development](https://travis-ci.com/cadox8/Ariray.svg?branch=development)](https://travis-ci.com/cadox8/Ariray)   |

Ariray is the future of the RPG for Minecraft

## Table Of Contents
- [Ariray](#Ariray)
- [Roadamp](#roadmap)
    - [Ariray-Core](#ariray-core)
    - [Ariray-Items](#ariray-items)
    - [Ariray-Structures](#ariray-structures)
- [Usage](#usage)
- [Get the plugins](#get-the-plugins)
    - [Download](#download)
    - [Build](#build)
- [Version History](#version-history)
- [License](#license)
## Roadmap

### Ariray-Core
- [ ] Skills System
    - [ ] Some default Skills
    - [ ] API to add own Skills
    - [ ] Skills can be leveled up based on experience
    - [ ] Own skills by class
    - [ ] Sub Skills (Abilities)
- [ ] Abilities System
    - [ ] Based on each Skill leveled
    - [ ] Chooseable
    - [ ] API to add own Abilities
- [ ] Party System
    - [ ] Level Up with your friend!
- [ ] Customize the experience
    - [ ] Create custom Mobs and Bosses
    - [ ] Create unique items
    - [ ] Brew amazing potions
    - [ ] New enchantments

### Ariray-Items
- [ ] Adds tons of new items and craftings to the game
- [ ] Items are Skill based!
- [ ] Tons of new enchantments

### Ariray-Structures
- [ ] Adds more structures into the Overworld, Nether and The End
- [ ] Adds a new Dimension...


More plugins to come...


## Usage

You can use add this plugins to your `pom.xml`and use them as dependency to develop anything.
The maven repo is located [here for the releases](https://cadox8.es/repo/releases/es/cadox8/Ariray) and [here for the snapshots](https://cadox8.es/repo/snapshots/es/cadox8/Ariray).

Put the following into your `pom.xml` to use them:

```xml
<repository>
    <id>cadpx8-repo</id>
    <url>https://cadox8.es/repo/%version%/</url>
</repository>
```
Replace `%version%` to `snapshots` or `releases`.

```xml
<dependency>
    <groupId>es.cadox8</groupId>
    <artifactId>Ariray</artifactId>
    <version>%version%</version>
</dependency>
```
Replace `%version%` with the version you want to use and `Ariray` with the module you want to import.
## Get the Plugins

You have two options to obtain the plugins:

Once you get all `.jar`, you have to copy them into the plugins folder. Then start the server and configure [config.yml](https://github.com/cadox8/Ariray/blob/master/Ariray-Core/src/config.yml).


Restart the server and everything will work properly.

### Download
You can download the latest release from [here](https://github.com/cadox8/Ariray/releases).

Additionally, you can download the SNAPSHOT versión from [here](https://github.com/cadox8/Ariray/releases).


### Build
Clone the project

```bash
  git clone https://github.com/cadox8/Ariray.git
```

Go to the project directory

```bash
  cd Ariray
```

Build the Project

```bash
  mvn clean install
```


## Version history

| Version  	|  Date 	|  Changelog 	|
|:-:	      |:-:	    |:-:	|
|  - 	      |  - 	    |  - 	|


## License

This project is released under [GPL-3.0 License](https://github.com/cadox8/Ariray/blob/master/LICENSE).