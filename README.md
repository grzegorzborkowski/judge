# judge

### Security part

### 1. Dodawanie nowego uzytkownika

[Sequence diagram](goo.gl/32rjQ7)


### 2. Call do endpointów które wymagają uwierzytelniania i autoryzacji

[Sequence diagram](https://goo.gl/j8Gfqb)

Używamy springowego Basic Auth.
Każdy request ma zawierać basic token (w nagłówku: "Authorization": "Basic YW5hZ3dpYTppbml0").
Token zawiera zakodowane username i password.

Mamy napisany CustomAuthenticationProvider który implementuje springowy interfejs AuthenticationProvider.
CustomAuthenticationProvider przetwarza token i wydziela password i hasło (odkodowane!).
W metodzie authenticate sprawdzamy czy w bazie istnieje użytkownik z podanym username i hasłem.
Żeby porównać odkodowane hasło wyciągnięte z tokena i zakodowane hasło znajdujące się w bazie, używamy PasswordService.
PasswordService udostepnia w tym celu funkcję validate (która wykonuje BCryptPasswordEncoder.match(plainPawssword, encodedPassword)).

Jeśli użytkownik istnieje w bazie, to zwracamy wszystkie jego Authorities (w naszym przypadku "roles" zapisane w bazie).
Jeśli nie ma takiego użytkownika albo nie zgadza się hasło, to zwracamy błąd uwierzytelnienia.

Dzięki temu, że po udanym uwierzytelnieniu zwracamy Authorities, możemy zarządzać dostępem do różnych zasobów,
np. tylko student ma dostęp do submissions itp.
