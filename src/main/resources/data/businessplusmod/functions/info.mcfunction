scoreboard objectives add number dummy {"text":"Number","color":"red"}

execute as @e[type=chest_minecart,tag=gui] at @s run tp @s ~ -60 ~
kill @e[type=chest_minecart,tag=gui]
give @a clock
tellraw @a [{"text":"SHOP","color":"aqua"},{"text":" has been loaded!","color":"gold"}]
function businessplusmod:tick
