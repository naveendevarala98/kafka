package org.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springboot.entity.Wikimedia;
import org.springboot.repository.WikimediaDataRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    private WikimediaDataRepository wikimediaDataRepository;

    public KafkaDatabaseConsumer(WikimediaDataRepository wikimediaDataRepository) {
        this.wikimediaDataRepository = wikimediaDataRepository;
    }

    @KafkaListener(topics = "wikimedia_recentchange", groupId = "myGroup")
    public void consumer(String eventMessage){

        logger.info(String.format("Event message received %s",eventMessage));
        Wikimedia wikimedia = new Wikimedia();
        wikimedia.setWikiEventData(eventMessage);

        wikimediaDataRepository.save(wikimedia);
    }
}
