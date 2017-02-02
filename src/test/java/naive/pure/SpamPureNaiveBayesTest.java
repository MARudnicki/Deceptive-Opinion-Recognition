package naive.pure;

import naive.Dataset;
import naive.PureNaiveBayesEngine;
import naive.TestAbstract;
import naive.classifiers.SpamClassfier;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Maciej Rudnicki on 02/02/2017.
 */
public class SpamPureNaiveBayesTest extends TestAbstract {

    protected Dataset<SpamClassfier> dataset;

    protected PureNaiveBayesEngine engine;

    @Before
    public void prepare() throws Exception {
        dataset = new Dataset<>(SpamClassfier.class);
        engine = new PureNaiveBayesEngine(dataset);
        prepareSpam();
        prepareHam();
    }

    private void prepareSpam() throws Exception {
        prepareMail(SpamClassfier.SPAM, "/datasets/spam-recognition/training-set/spam");
    }

    private void prepareHam() throws Exception {
        prepareMail(SpamClassfier.NOT_SPAM, "/datasets/spam-recognition/training-set/ham");
    }

    private void prepareMail(final SpamClassfier spamClassfier, String path) throws Exception {
        URL url = PureNaiveBayesLanguageTest.class.getResource(path);

        Map<URL, SpamClassfier> URLs = Files.walk(Paths.get(url.toURI()))
                .filter(Files::isRegularFile)
                .map(Path::toString)
                .map(s -> s.substring(s.lastIndexOf("target/classes") + 14))
                .map(SpamPureNaiveBayesTest.class::getResource)
                .collect(Collectors.toMap(URL -> URL, URL -> spamClassfier));

        dataset.train(URLs);
    }


    @Test
    public void spamRecognition() throws Exception {
        Enum spam = engine.predict("Subject: entex transistion\n" +
                "the purpose of the email is to recap the kickoff meeting held on yesterday\n" +
                "with members from commercial and volume managment concernig the entex account :\n" +
                "effective january 2000 , thu nguyen ( x 37159 ) in the volume managment group ,\n" +
                "will take over the responsibility of allocating the entex contracts . howard\n" +
                "and thu began some training this month and will continue to transition the\n" +
                "account over the next few months . entex will be thu ' s primary account\n" +
                "especially during these first few months as she learns the allocations\n" +
                "process and the contracts .\n" +
                "howard will continue with his lead responsibilites within the group and be\n" +
                "available for questions or as a backup , if necessary ( thanks howard for all\n" +
                "your hard work on the account this year ! ) .\n" +
                "in the initial phases of this transistion , i would like to organize an entex\n" +
                "\" account \" team . the team ( members from front office to back office ) would\n" +
                "meet at some point in the month to discuss any issues relating to the\n" +
                "scheduling , allocations , settlements , contracts , deals , etc . this hopefully\n" +
                "will give each of you a chance to not only identify and resolve issues before\n" +
                "the finalization process , but to learn from each other relative to your\n" +
                "respective areas and allow the newcomers to get up to speed on the account as\n" +
                "well . i would encourage everyone to attend these meetings initially as i\n" +
                "believe this is a critical part to the success of the entex account .\n" +
                "i will have my assistant to coordinate the initial meeting for early 1 / 2000 .\n" +
                "if anyone has any questions or concerns , please feel free to call me or stop\n" +
                "by . thanks in advance for everyone ' s cooperation . . . . . . . . . . .\n" +
                "julie - please add thu to the confirmations distributions list");
        System.out.println(spam.equals(SpamClassfier.NOT_SPAM));
        System.out.println(spam.equals(SpamClassfier.SPAM));
//
//
//        Enum spam2 = engine.predict("Hi George. Do you remember when we have math exam ?");
//        System.out.println(spam2);
//        Map<String, SpamClassfier> files = prepareSampleFiles();
//
//        long correctValues = files.entrySet().stream()
//                .filter(entry -> entry.getValue().equals(engine.predict(loadFile(entry.getKey()))))
//                .count();
//        long allValues = (long) files.entrySet().size();
//
//        System.out.println(String.join(" ", "Pure Naive Bayes predict language in", String.valueOf(correctValues), " on ", String.valueOf(allValues)));

    }
}
