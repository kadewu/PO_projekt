CREATE TABLE Zawodnik (
	_id INTEGER PRIMARY KEY,
	login varchar(255) NOT NULL UNIQUE,
	haslo varchar(255) NOT NULL,
	prawo_publikacji integer(1) NOT NULL,
	prawo_zawodowca integer(1) NOT NULL
);

CREATE TABLE Rozwiazanie (
	_id INTEGER PRIMARY KEY,
	czy_publiczne integer(1) NOT NULL,
	czas integer(10) NOT NULL,
	data_dodania time NOT NULL,
	czy_xcross integer(1) NOT NULL,
	czy_ollskip integer(1) NOT NULL,
	czy_pllskip integer(1) NOT NULL,
	czy_f2l integer(1) NOT NULL,
	ZawodnikID integer(10) NOT NULL,
	Algorytm_mieszajacyID integer(10) NOT NULL,
	FOREIGN KEY(ZawodnikID) REFERENCES Zawodnik(ID),
	FOREIGN KEY(Algorytm_mieszajacyID) REFERENCES Algorytm_mieszajacy(ID)
);

CREATE TABLE Algorytm_mieszajacy (
	_id INTEGER PRIMARY KEY,
	algorytm varchar(255) NOT NULL UNIQUE
);

INSERT INTO Zawodnik(_id, login, haslo, prawo_publikacji, prawo_zawodowca) VALUES (1, 'admin', 'admin', 1, 1);
INSERT INTO Algorytm_mieszajacy(_id, algorytm) VALUES (1, 'U'' F2 L2 D2 B'' L'' B'' U2 B2 L'' U R'' U'' L D'' B2 L D');
INSERT INTO Rozwiazanie(_id, czy_publiczne, czas, data_dodania, czy_xcross, czy_ollskip, czy_pllskip, czy_f2l, ZawodnikID, Algorytm_mieszajacyID)
VALUES (1, 1, 12340, '2019-01-01', 1, 0, 0, 1, 1, 1);