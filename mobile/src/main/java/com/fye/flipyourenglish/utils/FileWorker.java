package com.fye.flipyourenglish.utils;

import android.util.Log;

import com.fye.flipyourenglish.entities.Card;
import com.google.common.base.Objects;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class FileWorker {

    public static List<Card> readCards(File path)
    {
        File file = new File(path,"cards/cards");
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File read failed: " + e.toString());
        }

        return Objects.firstNonNull(Utils.gson.fromJson(text.toString(), new TypeToken<List<Card>>() {}.getType()), new ArrayList<>());
    }

    public static void writeCard(File path, List<Card> card)
    {
        File filePath = new File(path, "cards");
        if(!filePath.exists())
            filePath.mkdir();
        File file = new File(filePath, "cards");
        try
        {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(Utils.gson.toJson(card, new TypeToken<List<Card>>() {}.getType()));
            myOutWriter.close();

            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
