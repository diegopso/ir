package connection;

/**
 *
 * @author Diego
 */
public class ModelTrust {
    public Integer sink_id, source_id, trust_source_sink, trust_sink_source;

    public ModelTrust() {
    }

    public ModelTrust(Integer source_id, Integer sink_id, Integer trust_source_sink, Integer trust_sink_source) {
        this.sink_id = sink_id;
        this.source_id = source_id;
        this.trust_source_sink = trust_source_sink;
        this.trust_sink_source = trust_sink_source;
    }
}
