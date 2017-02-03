package naive.pure;

import naive.Dataset;
import naive.PureNaiveBayesEngine;
import naive.TestAbstract;
import naive.classifiers.SpamClassfier;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Maciej Rudnicki on 02/02/2017.
 */
public class PureNaiveBayesSpamTest extends TestAbstract {

    @Before
    public void prepare() throws Exception {
        dataset = new Dataset(SpamClassfier.class);
        engine = new PureNaiveBayesEngine(dataset);
        prepareSpam();
        prepareHam();
    }

    private void prepareSpam() throws Exception {
        prepareData(SpamClassfier.SPAM, "/datasets/spam-recognition/training-set/spam");
    }

    private void prepareHam() throws Exception {
        prepareData(SpamClassfier.NOT_SPAM, "/datasets/spam-recognition/training-set/ham");
    }

    @Test
    public void sampleHamRecognition() throws Exception {
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

        assertThat(spam, is(SpamClassfier.NOT_SPAM));
    }

    @Test
    public void sampleSpamRecognition() throws Exception {
        Enum spam = engine.predict("Subject: be a man\n" +
                "satisfied customers testimonials\n" +
                "jimmy - - - - 47 , male , uk\n" +
                "what you claim is wrong . my sperm volume didn ' t\n" +
                "increase by 500 % . it increased by zillion %\n" +
                "sharon - - - female , uk\n" +
                "my husband decided to try spur - m , and the results\n" +
                "are great ! i just love it when it starts spurting out\n" +
                "jose - - - 29 , male , usa\n" +
                "i cannot believe how good my semen has become . it is\n" +
                "a thick blob that shoots like a rocket . my wife says\n" +
                "she can feel the force with which my semen hits her\n" +
                "inside , which earlier she couldn ' t even feel . i don ' t\n" +
                "know about other customers but i am lovin it .\n" +
                "michael - - - 41 , male , hong kong\n" +
                "i always dreamt of shooting like a porn star and i\n" +
                "can do it now , my girl cannot eat as much as i can shoot .\n" +
                "my wife and i had been looking for a product to help\n" +
                "with boosting male fertility . i am happy to say that\n" +
                "test results have improved in the time i have been\n" +
                "using spur - m ( 2 months ) . thank you for your assistance ,\n" +
                "and for the supply of spur - m\n" +
                "m . rosenberg , nyc , usa\n" +
                "http : / / joseph . chorally . com / spur / ? sheep\n" +
                "not interested , pls go here\n" +
                "http : / / hare . degradedly . com / rm . php");

        assertThat(spam, is(SpamClassfier.SPAM));
    }

    @Test
    public void spamRecognition() throws Exception {

        Map<URL, Enum> URLsHam = prepareMapOfUrl("/datasets/spam-recognition/veryfication-set/ham",
                SpamClassfier.NOT_SPAM);

        long correctValuesHam = URLsHam.entrySet().stream()
                .filter(this::isPredictionCorrect)
                .count();
        long allValuesHam = (long) URLsHam.entrySet().size();

        System.out.println("Correct values : " + correctValuesHam + " of " + allValuesHam);

        Map<URL, Enum> URLsSpam = prepareMapOfUrl("/datasets/spam-recognition/veryfication-set/spam",
                SpamClassfier.SPAM);

        long correctValuesSpam = URLsSpam.entrySet().stream()
                .filter(this::isPredictionCorrect)
                .count();
        long allValuesSpam = (long) URLsSpam.entrySet().size();

        System.out.println("Correct values : " + correctValuesHam + " of " + allValuesHam);

        long correctValues = correctValuesHam + correctValuesSpam;
        long allValues = allValuesSpam + allValuesHam;
        double efficiency = (double) correctValues * 100 / allValues;
        System.out.println(String.join(" ",
                "SUMMARY Correct values : ",
                String.valueOf(correctValues),
                " of ",
                String.valueOf(allValues),
                "what gives",
                String.valueOf(efficiency),
                "% efficiency"));

        assertTrue(efficiency > 85);
    }
}
