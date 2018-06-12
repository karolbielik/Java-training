package com.insdata.streams;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

//TODO: spravit spliterator na nacitanie riadkov suboru
public class Test06Spliterator {
/*
    https://codereview.stackexchange.com/questions/52050/spliterator-implementation?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
*/
    public static void main(String[] args) {
        Test06Spliterator urlScraper = new Test06Spliterator("http://www.wikipedia.org");
        urlScraper.parallelStream()
                .forEach(System.out::println);
    }



        private String url;
        private Set<String> index = Collections.synchronizedSet(new TreeSet<>());
        private List<String> startingReferences = new ArrayList<>();

        public Test06Spliterator(String url) {
            this.url = url;
        }

        public Stream<String> stream() {
            startingReferences.add(url);
            index.add(url);
            return StreamSupport.stream(new UrlSpliterator(startingReferences, index), false);
        }

        public Stream<String> parallelStream() {
            startingReferences.add(url);
            index.add(url);
            return StreamSupport.stream(new UrlSpliterator(startingReferences, index), true);
        }

        private static class UrlSpliterator implements Spliterator<String> {

            private static final int THRESHOLD = 10;
            // variable list of urls in the spliterator
            List<String> refs;
            // distinct set of already known urls
            Set<String> index;
            // # urls returned by the stream
            int examined = 0;
            // position of last scraped url
            int scraped = -1;

            UrlSpliterator(List<String> refs, Set<String> index) {
                this.refs = refs;
                this.index = index;

            }

            @Override
            // the stream will contain distinct urls
            public int characteristics() {
                return DISTINCT | NONNULL | IMMUTABLE;
            }

            @Override
            // stream will be infinite as web
            public long estimateSize() {
                return Long.MAX_VALUE;
            }

            @Override
            public boolean tryAdvance(Consumer<? super String> consumer) {
                if (examined < refs.size()) {
                    consumer.accept(refs.get(examined));

                    // when under threshold, a new url is scraped
                    // to fill the list
                    if (refs.size() - examined < THRESHOLD) {
                        scraped++;
                        analyze(refs.get(scraped));
                    }

                    examined++;
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public Spliterator<String> trySplit() {
                int n = refs.size() - examined;
                // when I have more than one url, I split the scraping
                // into two parts. New Spliterator will receive a new
                // arraylist that contains half of the urls to be
                // returned by the stream. Index will be passed as is
                // since we do not want duplicates in urls
                if (n > 1) {
                    int splitPoint = examined + n / 2;

                    ArrayList<String> al = new ArrayList<>();
                    al.addAll(splitPoint, refs);
                    for (int i = refs.size() - 1; i >= splitPoint; i--)
                        refs.remove(i);
                    return new UrlSpliterator(al, index);

                }
                return null;

            }

            // this is the core of scraping, I use Jsoup to do the
            // dirty part of the job
            private void analyze(String aUrl) {
                Document doc;
                Elements links = null;
                try {

                    doc = Jsoup.connect(aUrl).get();

                    links = doc.select("a[href]");
                    for (Element link : links) {
                        String newUrl = (String) link.attr("abs:href");
                        if (!index.contains(newUrl)) {
                            refs.add(newUrl);
                            index.add(newUrl);
                        }
                    }
                } catch (IOException e) {
                    ; // if a link is broken, not a problem. It can happens
                    // but I do not want to stop scraping
                }

            }

        }



}
