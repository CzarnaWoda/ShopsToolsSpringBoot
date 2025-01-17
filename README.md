# Tools and Shops API Documentation

## **Informacje podstawowe**
- **Tytuł:** Tools and Shops API Documentation
- **Opis:** Dokumentacja API dla zarządzania narzędziami i sklepami.
- **Wersja:** 1.0
- **Serwer URL:** `http://localhost:8080`

---

## **Spis treści**
1. [Opis](#opis)
2. [Endpointy](#endpointy)
    - [Pobierz listę sklepów](#pobierz-listę-sklepów)
    - [Pobierz sklep po nazwie](#pobierz-sklep-po-nazwie)
    - [Pobierz sklep po ID](#pobierz-sklep-po-id)
    - [Utwórz nowy sklep](#utwórz-nowy-sklep)
    - [Zaktualizuj sklep](#zaktualizuj-sklep)
    - [Usuń sklep](#usuń-sklep)
3. [Schematy](#schematy)
    - [ShopCreateRequest](#shopcreaterequest)
    - [ShopUpdateRequest](#shopupdaterequest)
    - [HttpResponse](#httpresponse)

---

## **Opis**
API umożliwia zarządzanie sklepami w systemie. Możesz:
- Pobierać listy sklepów.
- Wyszukiwać sklepy po nazwie lub ID.
- Tworzyć nowe sklepy.
- Aktualizować istniejące sklepy.
- Usuwać sklepy.

---

## **Endpointy**

### **Pobierz listę sklepów**
**GET** `/api/v1/shop/shops`

- **Opis:** Pobiera listę sklepów z uwzględnieniem paginacji i sortowania.
- **Parametry zapytania:**
  | Parametr  | Lokalizacja | Typ     | Opis                           | Przykład |
  |-----------|-------------|---------|--------------------------------|----------|
  | `page`    | `query`     | integer | Numer strony                   | `0`      |
  | `size`    | `query`     | integer | Rozmiar strony                 | `10`     |
  | `sortBy`  | `query`     | string  | Pole do sortowania             | `id`     |
  | `sortDir` | `query`     | string  | Kierunek sortowania (asc/desc) | `asc`    |

- **Odpowiedzi:**
  - `200 OK` – Lista sklepów została zwrócona.

---

### **Pobierz sklep po nazwie**
**GET** `/api/v1/shop/name/{shopName}`

- **Opis:** Pozwala pobrać sklep po jego nazwie.
- **Parametry ścieżki:**
  | Parametr   | Lokalizacja | Typ     | Opis                 | Przykład               |
  |------------|-------------|---------|----------------------|------------------------|
  | `shopName` | `path`      | string  | Nazwa sklepu         | `Sklep z narzędziami`  |

- **Odpowiedzi:**
  - `200 OK` – Sklep z podaną nazwą został znaleziony.
  - `404 Not Found` – Sklep z podaną nazwą nie został znaleziony.

---

### **Pobierz sklep po ID**
**GET** `/api/v1/shop/id/{id}`

- **Opis:** Pobiera sklep po jego ID.
- **Parametry ścieżki:**
  | Parametr | Lokalizacja | Typ     | Opis            | Przykład |
  |----------|-------------|---------|-----------------|----------|
  | `id`     | `path`      | integer | ID sklepu       | `1`      |

- **Odpowiedzi:**
  - `200 OK` – Sklep z podanym ID został znaleziony.
  - `404 Not Found` – Sklep z podanym ID nie został znaleziony.

---

### **Utwórz nowy sklep**
**POST** `/api/v1/shop/create`

- **Opis:** Tworzy nowy sklep zgodnie z przesłanymi danymi.
- **Body requestu:**
  ```json
  {
    "name": "Nowy Sklep",
    "email": "kontakt@nowysklep.pl"
  }
  ```

- **Odpowiedzi:**
  - `201 Created` – Sklep został utworzony.
  - `400 Bad Request` – Sklep o takich parametrach już istnieje.

---

### **Zaktualizuj sklep**
**PUT** `/api/v1/shop/update/{id}`

- **Opis:** Aktualizuje dane sklepu na podstawie przesłanych informacji.
- **Parametry ścieżki:**
  | Parametr | Lokalizacja | Typ     | Opis                       | Przykład |
  |----------|-------------|---------|----------------------------|----------|
  | `id`     | `path`      | integer | ID sklepu do zaktualizowania | `1`      |

- **Body requestu:**
  ```json
  {
    "shopName": "Zaktualizowany Sklep",
    "email": "kontakt@zaktualizowanysklep.pl"
  }
  ```

- **Odpowiedzi:**
  - `200 OK` – Sklep został zaktualizowany.
  - `400 Bad Request` – Sklep z taką nazwą już istnieje.
  - `404 Not Found` – Sklep z podanym ID nie istnieje.

---

### **Usuń sklep**
**DELETE** `/api/v1/shop/delete/{id}`

- **Opis:** Usuwa sklep na podstawie ID.
- **Parametry ścieżki:**
  | Parametr | Lokalizacja | Typ     | Opis            | Przykład |
  |----------|-------------|---------|-----------------|----------|
  | `id`     | `path`      | integer | ID sklepu       | `1`      |

- **Odpowiedzi:**
  - `200 OK` – Sklep został pomyślnie usunięty.
  - `404 Not Found` – Sklep z podanym ID nie istnieje.

---

## **Schematy**

### **ShopCreateRequest**
- **Opis:** Request zawierający dane nowego sklepu.
- **Struktura:**
  | Pole  | Typ     | Opis                        | Przykład                  |
  |-------|---------|-----------------------------|---------------------------|
  | `name` | string  | Nazwa sklepu (6-50 znaków) | `"Nowy Sklep"`           |
  | `email`| string  | Email kontaktowy sklepu    | `"kontakt@nowysklep.pl"` |

### **ShopUpdateRequest**
- **Opis:** Request zawierający dane do aktualizacji sklepu.
- **Struktura:**
  | Pole      | Typ     | Opis                        | Przykład                           |
  |-----------|---------|-----------------------------|------------------------------------|
  | `shopName`| string  | Nowa nazwa sklepu (6-50 znaków) | `"Zaktualizowany Sklep"`         |
  | `email`   | string  | Nowy email kontaktowy sklepu | `"kontakt@zaktualizowanysklep.pl"` |

### **HttpResponse**
- **Opis:** Standardowa odpowiedź z endpointów API.
- **Struktura:**
  | Pole        | Typ     | Opis                                     |
  |-------------|---------|------------------------------------------|
  | `timeStamp` | string  | Aktualny czas odpowiedzi                 |
  | `status`    | string  | Status odpowiedzi (np. `200 OK`)         |
  | `statusCode`| integer | Kod statusu odpowiedzi                   |
  | `reason`    | string  | Powód odpowiedzi                        |
  | `message`   | string  | Wiadomość informacyjna                  |
  | `data`      | object  | Dane zwrócone w odpowiedzi (jeśli występują) |

