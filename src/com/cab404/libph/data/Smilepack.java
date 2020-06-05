package com.cab404.libph.data;

import java.util.ArrayList;
import java.util.List;

public class Smilepack {

    public final List<KV<String, String>> smiles;

    public Smilepack() {
        smiles = new ArrayList<>();
    }

    public String getLink(String id) {
        for (KV<String, String> e : smiles)
            if (e.k.equals(id))
                return e.v;
        return null;
    }

    public void add(String id, String link){
        smiles.add(new KV<>(id, link));
    }
}
