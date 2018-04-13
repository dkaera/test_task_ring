package com.testproject.kaera.ringtestapp.service.util;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RxPreferences {

    public static final String RING_PREFERENCES = "ring_preferences";

    private RxSharedPreferences rxSharedPreferences;
    private Gson gson;

    public RxPreferences(SharedPreferences preferences, Gson gson) {
        this.rxSharedPreferences = RxSharedPreferences.create(preferences);
        this.gson = gson;
    }

    public Preference<Boolean> getBoolean(@NonNull String key) {
        return rxSharedPreferences.getBoolean(key);
    }

    public Preference<Boolean> getBoolean(@NonNull String key, @NonNull Boolean defaultValue) {
        return rxSharedPreferences.getBoolean(key, defaultValue);
    }

    public <T extends Enum<T>> Preference<T> getEnum(@NonNull String key, @NonNull T defaultValue, @NonNull Class<T> enumClass) {
        return rxSharedPreferences.getEnum(key, defaultValue, enumClass);
    }

    public Preference<Float> getFloat(@NonNull String key) {
        return rxSharedPreferences.getFloat(key);
    }

    public Preference<Float> getFloat(@NonNull String key, @NonNull Float defaultValue) {
        return rxSharedPreferences.getFloat(key, defaultValue);
    }

    public Preference<Integer> getInteger(@NonNull String key) {
        return rxSharedPreferences.getInteger(key);
    }

    public Preference<Integer> getInteger(@NonNull String key, @NonNull Integer defaultValue) {
        return rxSharedPreferences.getInteger(key, defaultValue);
    }

    @NonNull
    public Preference<Long> getLong(@NonNull String key) {
        return rxSharedPreferences.getLong(key);
    }

    @Nullable
    public Preference<Long> getLong(@NonNull String key, @Nullable Long defaultValue) {
        return rxSharedPreferences.getLong(key, defaultValue);
    }

    @NonNull
    public <T> Preference<T> getObject(@NonNull String key, @NonNull T defaultValue, @NonNull Type clazz) {
        return rxSharedPreferences.getObject(key, defaultValue, new GsonPreferenceAdapter(gson, clazz));
    }

    public Preference<String> getString(@NonNull String key) {
        return rxSharedPreferences.getString(key);
    }

    public Preference<String> getString(@NonNull String key, @Nullable String defaultValue) {
        return rxSharedPreferences.getString(key, defaultValue);
    }

    public Preference<Set<String>> getStringSet(@NonNull String key) {
        return rxSharedPreferences.getStringSet(key);
    }

    public Preference<Set<String>> getStringSet(@NonNull String key, @NonNull Set<String> defaultValue) {
        return rxSharedPreferences.getStringSet(key, defaultValue);
    }

    public <T> Preference<List<T>> getList(@NonNull String key, @NonNull Class<T> clazz) {
        return rxSharedPreferences.getObject(key, new ArrayList<T>(), new ListAdapter(gson, clazz));
    }

    private class ListAdapter<T> implements Preference.Adapter<List<T>> {

        private Class<T> clazz;
        private Gson gson;

        public ListAdapter(Gson gson, Class<T> clazz) {
            this.clazz = clazz;
            this.gson = gson;
        }

        @Override
        public List<T> get(@NonNull String key, @NonNull android.content.SharedPreferences preferences) {
            String[] list = TextUtils.split(preferences.getString(key, ""), "‚‚");
            List<T> result = new ArrayList<>(list.length);
            for (String item : list) {
                result.add(gson.fromJson(item, clazz));
            }
            return result;
        }

        @Override
        public void set(@NonNull String key, @NonNull List<T> value, @NonNull SharedPreferences.Editor editor) {
            ArrayList<String> objStrings = new ArrayList<>();
            for (T obj : value) {
                objStrings.add(gson.toJson(obj));
            }
            String[] myStringList = objStrings.toArray(new String[objStrings.size()]);
            editor.putString(key, TextUtils.join("‚‚", myStringList)).apply();
        }
    }

    private class GsonPreferenceAdapter<T> implements Preference.Adapter<T> {

        private Gson gson;
        private Type clazz;

        public GsonPreferenceAdapter(Gson gson, Type clazz) {
            this.gson = gson;
            this.clazz = clazz;
        }

        @Override
        public T get(String key, SharedPreferences preferences) {
            return gson.fromJson(preferences.getString(key, ""), clazz);
        }

        @Override
        public void set(String key, T value, SharedPreferences.Editor editor) {
            editor.putString(key, gson.toJson(value));
        }
    }
}
