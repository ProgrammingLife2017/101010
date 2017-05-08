package Datastructure;

import com.koloboke.collect.map.hash.HashIntObjMaps;
import com.koloboke.collect.map.hash.HashIntObjMap;
import org.h2.Driver;

/**
 * Created by DexterQuintin on 5/8/2017.
 */
public class NodeMap {
    HashIntObjMap<int[]> map;
    Driver database;

    public NodeMap() {
        this.map = HashIntObjMaps.<int[]>newUpdatableMap(0);
        this.database = new Driver();
    }

    public NodeMap(HashIntObjMap<int[]> map, Driver database) {
        this.map = map;
        this.database = database;
    }
}
