CREATE TABLE IF NOT EXISTS orders (
idOrder VARCHAR(255) NOT NULL PRIMARY KEY,
time DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS engines (
idEngine VARCHAR(255) NOT NULL PRIMARY KEY,
typeEngine VARCHAR(255) NOT NULL,
power INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS cars (
idCar VARCHAR(255) NOT NULL PRIMARY KEY,
manufacturer VARCHAR(255) NOT NULL,
color VARCHAR(255) NOT NULL,
count INTEGER NOT NULL,
price INTEGER NOT NULL,
typeCar VARCHAR(255) NOT NULL,
engineID VARCHAR(255) NOT NULL,
orderID VARCHAR(255),
FOREIGN KEY (engineID) REFERENCES engines (idEngine) ON DELETE CASCADE,
FOREIGN KEY (orderID) REFERENCES orders (idOrder) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS passenger_cars (
idCar VARCHAR(255) NOT NULL PRIMARY KEY,
passengerCount INTEGER NOT NULL,
FOREIGN KEY (idCar) REFERENCES cars (idCar) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS truck_cars (
idCar VARCHAR(255) NOT NULL PRIMARY KEY,
loadCapacity INTEGER NOT NULL,
FOREIGN KEY (idCar) REFERENCES cars (idCar) ON DELETE CASCADE
);