package com.hrzafer.prizma.preprocessing;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class StandardTokenizerTest {
    @Test
    public void testTokenize() throws Exception {
        StandardTokenizer tokenizer = new StandardTokenizer();
        String str = "EN__ HAKKIMIZD_A Vizyo'n / 12Misyon Hede12fler / 12 12Stratejiler TBV Resmi Senedi Organizasyon FAALİYETLER Projeler Tamamlanmış Projeler Süren Projeler TBV Bilgi Toplumu Enstitüsü KÜTÜPHANE Raporlar BT Hukuku Yasalarla ilgili TBV Görüşleri Bilişim Hukuku İle İlgili Yasal Düzenlemeler Bağlantılar MEDYA Haber Basın Bültenleri Basın Odası BİZE ULAŞIN İletişim Bilgileri MEDYA Bu Ay Telepati Telekom ULAŞTIRMA DENİZCİLİK VE HABERLEŞME BAKANLIĞI VE VODAFONE, 'BEN DE VARIM' PROJESİ Telepati Telekom SAYFA DOKÜMANI Dokümanı indir. Videolar Duyurular Linked IN Twitter Facebook HAKKIMIZDA Vizyon / Misyon Hedefler / Stratejiler TBV Resmi Senedi Organizasyon FAALİYETLER Projeler TBV Bilgi Toplumu Enstitüsü KÜTÜPHANE Raporlar BT Hukuku Bağlantılar MEDYA Haber Basın Bültenleri Basın Odası BİZE ULAŞIN İletişim Bilgileri © Copyright 2013 Türkiye Bilişim Vakfı - Tüm Hakları Saklıdır";
        List<String> tokenize = tokenizer.tokenize(str);
        String resultString[] = str.replaceAll("[^\\p{L}\\p{Nd}]", " ").split("\\s+");
        System.out.println(tokenize);
        System.out.println(Arrays.asList(resultString));
    }
}
