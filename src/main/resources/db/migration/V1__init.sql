DROP TABLE IF EXISTS relay_timer;
CREATE TABLE relay_timer(
  id INT auto_increment PRIMARY KEY,
  name NVARCHAR(255),
  begin_time TIMESTAMP WITH TIME ZONE,
  end_time TIMESTAMP WITH TIME ZONE,
  power_consumption INT NOT NULL,
  periods_quantity INT NOT NULL,
  heating_power INT NOT NULL,
  controller_hostname NVARCHAR(255));