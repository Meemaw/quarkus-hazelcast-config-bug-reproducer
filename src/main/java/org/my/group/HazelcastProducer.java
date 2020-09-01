package org.my.group;

import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import io.quarkus.runtime.ShutdownEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class HazelcastProducer {

  private HazelcastInstance instance;

  /**
   * Lazy instantiate hazelcast instance for faster startup.
   *
   * @return hazelcast instance
   */
  public HazelcastInstance getInstance() {
    if (instance == null) {
      instance = Hazelcast.newHazelcastInstance(new XmlConfigBuilder().build());
    }
    return instance;
  }

  /**
   * Gracefully terminate hazelcast instance.
   *
   * @param event shutdown event
   */
  public void shutdown(@Observes ShutdownEvent event) {
    if (instance != null) {
      instance.shutdown();
    }
  }

}
