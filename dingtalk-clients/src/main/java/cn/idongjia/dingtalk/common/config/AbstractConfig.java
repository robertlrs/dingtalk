package cn.idongjia.dingtalk.common.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return (String) get(key);
    }

    protected Object get(String key){
        if (!values.containsKey(key))
            throw new ConfigException(String.format("Unknown configuration '%s'", key));

        return values.get(key);
    }
}
