package cn.idongjia.dingtalk.common.config;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AbstractConfig {

    private final Map<String, Object> values = new HashMap<>();

    public AbstractConfig(Map<?, ?> originals){
        for (Map.Entry<?, ?> entry : originals.entrySet()){
            if (!(entry.getKey() instanceof String))
                throw new ConfigException(entry.getKey().toString(), entry.getValue(), "Key must be a string.");

            values.put((String) entry.getKey(), entry.getValue());
        }

    }


    public Short getShort(String key) {
        return (Short) get(key);
    }

    public Integer getInt(String key) {
        return Integer.parseInt((String) get(key));
    }

    public Long getLong(String key) {
        return (Long) get(key);
    }

    public Double getDouble(String key) {
        return (Double) get(key);
    }

    @SuppressWarnings("unchecked")
    public List<String> getList(String key) {
        return (List<String>) get(key);
    }

    public Boolean getBoolean(String key) {
        return (Boolean) get(key);
    }

    public String getString(String key) {
        String ret = (String) get(key);
        if (StringUtils.isBlank(ret)){
            throw new ConfigException(String.format("empty configuration  for '%s'", key));
        }

        return (String) get(key);
    }

    protected Object get(String key){
        if (!values.containsKey(key))
            throw new ConfigException(String.format("Unknown configuration '%s'", key));

        return values.get(key);
    }

    public List<String> getKeys(){
        Set<String> keys = new HashSet<>();
        for (Map.Entry<String, Object> entry : this.values.entrySet()) {
            keys.add(entry.getKey());
        }

        return keys.stream().collect(Collectors.toList());
    }
}
