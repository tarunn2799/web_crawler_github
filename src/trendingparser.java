public class trendingparser {
    import com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

    /**
     *
     * @author Tarun
     */
    public class TrendingParser {

        public static void main(String[] args) {

            // first create file object for file placed at location
            // specified by filepath
            // Change filepath to your csv file"
            File file = new File("E:/trending.csv");
            List<String[]> data = new ArrayList<>();

            try {
                Document document = Jsoup.connect("https://github.com/trending").get();
                Elements lists = document.select("ol.repo-list li");

                // create FileWriter object with file as parameter
                FileWriter outfile = new FileWriter(file);

                // create CSVWriter object filewriter object as parameter
                CSVWriter writer = new CSVWriter(outfile);

                // Heading for csv
                data.add(new String[]{"Name", "Stars", "Language"});

                for (Element list : lists) {
                    String name = list.select("h3 a").attr("href");
                    String stargazers = "a[href=" + name + "/stargazers]";
                    String stars = list.select(stargazers).first().text();
                    String lang = list.select("span[itemprop=programmingLanguage]").text();

                    //NaN if no language found
                    if (lang.equals("")) {
                        lang = "NaN";
                    }
                    System.out.println(stars + " " + lang + " " + name.substring(1));
                    data.add(new String[]{name.substring(1), stars, lang});
                }

                writer.writeAll(data);

                // closing writer connection
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
