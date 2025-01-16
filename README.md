

# **Tools and Shops API Documentation**

Dokumentacja API dla zarządzania narzędziami i sklepami.

## **Informacje podstawowe**
- **Wersja:** 1.0
- **Serwer URL:** `http://localhost:8080`

---

## **Spis treści**
1. [Opis](#opis)
2. [Endpointy](#endpointy)
   - [Pobierz listę sklepów](#pobierz-listę-sklepów)
   - [Pobierz sklep po nazwie](#pobierz-sklep-po-nazwie)
   - [Pobierz sklep po ID](#pobierz-sklep-po-id)
3. [Przykładowe odpowiedzi](#przykładowe-odpowiedzi)
4. [Schematy](#schematy)

---

## **Opis**

API umożliwia:
- Pobieranie listy sklepów.
- Wyszukiwanie sklepu po nazwie.
- Pobieranie sklepu na podstawie jego ID.

---

## **Endpointy**

### **Pobierz listę sklepów**
**GET** `/api/v1/shop/shops`

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

- **Parametry ścieżki:**
  | Parametr     | Lokalizacja | Typ   | Opis                 | Przykład               |
  |--------------|-------------|-------|----------------------|------------------------|
  | `shopName`   | `path`      | string| Nazwa sklepu         | `Sklep z narzędziami`  |

- **Odpowiedzi:**
  - `200 OK` – Sklep z podaną nazwą został znaleziony.
  - `404 Not Found` – Sklep z podaną nazwą nie został znaleziony.

---

### **Pobierz sklep po ID**
**GET** `/api/v1/shop/id/{id}`

- **Parametry ścieżki:**
  | Parametr | Lokalizacja | Typ     | Opis            | Przykład |
  |----------|-------------|---------|-----------------|----------|
  | `id`     | `path`      | integer | ID sklepu       | `1`      |

- **Odpowiedzi:**
  - `200 OK` – Sklep z podanym ID został znaleziony.
  - `404 Not Found` – Sklep z podanym ID nie został znaleziony.

---

## **Przykładowe odpowiedzi**

Przykład odpowiedzi zwracanej przez API:
```json
{
  "timeStamp": "2025-01-16T12:00:00Z",
  "status": "200 OK",
  "statusCode": 200,
  "reason": "Request successful",
  "message": "Shops retrieved successfully",
  "data": {
    "shops": [
      {
        "id": 1,
        "name": "Sklep z narzędziami",
        "location": "Warszawa"
      },
      {
        "id": 2,
        "name": "Sklep ogrodniczy",
        "location": "Kraków"
      }
    ]
  }
}
```

---

## **Schematy**

### **HttpResponse**
Standardowa odpowiedź zwracana przez API:

| Pole        | Typ     | Opis                                     |
|-------------|---------|------------------------------------------|
| `timeStamp` | string  | Aktualny czas odpowiedzi                 |
| `status`    | string  | Status odpowiedzi (np. `200 OK`)         |
| `statusCode`| integer | Kod statusu odpowiedzi                   |
| `reason`    | string  | Powód odpowiedzi                        |
| `message`   | string  | Wiadomość informacyjna                  |
| `data`      | object  | Dane zwrócone w odpowiedzi (jeśli występują) |

---
