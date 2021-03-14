execute as @p[scores={money=..4}] run tellraw @s {"text":"You do not have enough Money to purchase this item!","color":"red"}
execute as @p[scores={money=..4}] at @s run playsound minecraft:block.note_block.pling ambient @s ~ ~ ~ 5 0

execute as @p[scores={money=5..}] run give @s minecraft:iron_ingot 1
execute as @p[scores={money=5..}] run tellraw @s {"text":"You have purchased this item!","color":"green"}
execute as @p[scores={money=5..}] at @s run playsound minecraft:block.note_block.pling ambient @s ~ ~ ~ 5 2
scoreboard players remove @p[scores={money=5..}] money 5