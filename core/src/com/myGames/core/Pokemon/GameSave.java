package com.myGames.core.Pokemon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class GameSave {
    private static final String SAVE_FILE_PATH = "save.json";

    public static void saveGame() {
        Player player = Player.getInstance();
        String resultJson = saveToJson(player.getPokemonBag(), player.getPotionsBag());
        writeToSaveFile(resultJson);
    }

    private static String saveToJson(
            AllyPokemon[] pokemon,
            Map<Potions, Integer> potions
    ) {
        Json jsonObject = new Json();
        StringWriter jsonText = new StringWriter();
        JsonWriter writer = new JsonWriter(jsonText);
        jsonObject.setOutputType(JsonWriter.OutputType.json);
        jsonObject.setWriter(writer);
        jsonObject.writeObjectStart();

        jsonObject.writeArrayStart("pokemon");

        jsonObject.writeObjectStart();

        jsonObject.writeValue("name", pokemon[0].getName());
        jsonObject.writeValue("type", pokemon[0].getType().toString());

        jsonObject.writeArrayStart("moveSet");

        for (Move move : pokemon[0].getMoveSet()) {
            if (move != null) {
                jsonObject.writeObjectStart();

                jsonObject.writeValue("name", move.getName());
                jsonObject.writeValue("type", move.getType());
                jsonObject.writeValue("damage", move.getDamage());

                jsonObject.writeObjectEnd();
            }
        }

        jsonObject.writeArrayEnd();

        jsonObject.writeValue("level", pokemon[0].getLevel());
        jsonObject.writeValue("experience", pokemon[0].getExp());

        jsonObject.writeObjectEnd();

        jsonObject.writeArrayEnd();
        jsonObject.writeValue("potions", potions.getOrDefault(Potions.Potion, 0));

        jsonObject.writeObjectEnd();

        return jsonObject.prettyPrint(jsonObject.getWriter().getWriter().toString());
    }

    public static void loadSave() {
        Player player = Player.getInstance();

        JsonReader jsonReader = new JsonReader();
        String jsonString = readFromSaveFile();
        JsonValue base = jsonReader.parse(jsonString);

        for (JsonValue nextPokemon : base.get("pokemon").iterator()) {
            String pokemonName = nextPokemon.getString("name");
            Type type = Type.valueOf(nextPokemon.getString("type"));

            int index = 0;
            Move[] moveSet = new Move[Pokemon.MAX_MOVES];
            for (JsonValue nextMove : nextPokemon.get("moveSet").iterator()) {
                String moveName = nextMove.getString("name");
                Move move = Info.ALL_MOVES.get(moveName);
                moveSet[index] = move;
                index++;
            }

            int level = nextPokemon.getInt("level");
            float exp = nextPokemon.getFloat("exp");

            AllyPokemon pokemon = new AllyPokemon(pokemonName, type, moveSet, level, exp);
            player.addPokemon(pokemon);
        }

        int potions = base.getInt("potions");
        player.addPotion(Potions.Potion, potions);
    }

    private static String readFromSaveFile() {
        InputStream saveData = Gdx.files.local(SAVE_FILE_PATH).read();

        String jsonString = new BufferedReader(
            new InputStreamReader(saveData, StandardCharsets.UTF_8)
        )
        .lines()
        .collect(Collectors.joining(System.lineSeparator()));

        return jsonString;
    }

    private static void writeToSaveFile(String jsonString) {
        InputStream saveData = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
        Gdx.files.local(SAVE_FILE_PATH).write(saveData, false);
    }
}
