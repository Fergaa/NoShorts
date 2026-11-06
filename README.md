# NoShorts

Mała aplikacja na Androida, która blokuje Shorts w aplikacji YouTube. Gdy otworzy się odtwarzacz Shorts, automatycznie wraca do poprzedniego ekranu.

## 1. Zbudowanie APK na GitHubie

1. Utwórz nowe repozytorium (może być prywatne).
2. Wrzuć do niego całą zawartość tego folderu, razem z ukrytym folderem `.github`.
   Najprościej: na stronie repozytorium wybierz **Add file → Upload files** i przeciągnij pliki.
   Uwaga: folder `.github` jest ukryty, sprawdź, czy się wgrał.
3. Wejdź w zakładkę **Actions**. Budowanie ruszy samo (ok. 3 do 5 minut).
   Jeśli nie ruszyło: **Build APK → Run workflow**.
4. Po zakończeniu kliknij gotowe zadanie i na dole pobierz **NoShorts-apk** (plik zip z APK w środku).

## 2. Instalacja na telefonie (Samsung)

1. Rozpakuj zip i otwórz `app-debug.apk` na telefonie.
2. Zezwól na instalację z tego źródła, gdy telefon zapyta.
3. Jeśli pojawi się Play Protect, wybierz **Więcej szczegółów → Zainstaluj mimo to**.

## 3. Włączenie usługi

1. **Ustawienia → Aplikacje → NoShorts → menu ⋮ → Zezwól na ustawienia z ograniczeniami**
   (Android 13 i nowszy; bez tego przełącznik usługi jest wyszarzony).
2. **Ustawienia → Ułatwienia dostępu → Zainstalowane aplikacje → NoShorts → Włącz.**
   Kliknięcie ikony NoShorts też otwiera te ustawienia.

## 4. Żeby Samsung nie wyłączał usługi

**Ustawienia → Bateria → Limity użycia w tle → Aplikacje, które nigdy nie są usypiane → dodaj NoShorts.**

## Bateria

Usługa dostaje zdarzenia tylko z aplikacji YouTube i sprawdza ekran najwyżej co 300 ms. Gdy YouTube jest zamknięty, nic nie robi. Zużycie jest pomijalne.

## Gdy przestanie działać

YouTube co jakiś czas zmienia wewnętrzne nazwy widoków. Wtedy w pliku
`app/src/main/java/pl/noshorts/ShortsBlockerService.kt` trzeba dopisać nowy identyfikator do listy `SHORTS_VIEW_IDS` i zbudować APK jeszcze raz.

Przy instalacji nowej wersji odinstaluj najpierw starą (każde budowanie na GitHubie podpisuje APK innym kluczem testowym).
