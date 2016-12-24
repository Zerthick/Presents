# Presents! [![Github All Releases](https://img.shields.io/github/downloads/zerthick/Presents/total.svg)](https://github.com/Zerthick/Presents/releases) [![GitHub issues](https://img.shields.io/github/issues/zerthick/Presents.svg)](https://github.com/Zerthick/Presents/issues) [![license](https://img.shields.io/github/license/zerthick/Presents.svg)](https://github.com/Zerthick/Presents/blob/master/LICENSE.txt)
Presents! is a simple gift sharing plugin, players can send gifts to each other and recieve randomly generated presents. 
When the `/presents deliver` command is executed, a chest will be spawned at each player's delivery location and filled with presents!
 
##Commands

    /presents - Displays version information about the Presents Plugin. (Aliases: presents, gifts)
    /presents send <User> [Note] - Sends the item currently held in your main hand to User with an optional Note text (Notes support color codes!).
    /presents deliver - Delivers all presents!
    /presents createRandom <User> [Note] - Creates a gift to be given randomly from the item currently held in your main hand with an optional Note (Notes support color codes!). (Aliases: createRandom, rand) 
    /presents set deliveryLocation [User] - Sets your delivery location or the delivery location of User to your current location
    /presents set naughty <User> <true|false> - Sets the naughty status of User, naughty users get coal! (Default: false)
    /presents set randomDefault <Amount> - Sets the amount of randomly generated presents to give to each player. (Default: 5)
    /presents set coalSender <Name> - Sets the name of the person who sends coal! (Default: The Grinch)
    
##Permissions
    
    presents.command
    presents.command.send
    presents.command.deliver
    presents.command.create.random
    presents.command.set.deliverylocation.self
    presents.command.set.deliverylocation.other
    presents.command.set.naughty
    presents.command.set.randomdefault
    presents.command.set.coalsender