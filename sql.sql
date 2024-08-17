create database hospital_erp;
use hospital_erp;


CREATE TABLE `user` (
  `user_id` CHAR(36) NOT NULL UNIQUE,
  `first_name` VARCHAR(100) NOT NULL,
  `last_name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `mobile_number` VARCHAR(20) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `role` VARCHAR(12) NOT NULL CHECK (`role` IN ('Admin', 'User', 'Doctor','Patient','Pharmacist','Secretary','Nurse','Technician')),
  `gender` VARCHAR(12) NOT NULL CHECK (`gender` IN ('Male', 'Female')),
  `status` VARCHAR(12) NOT NULL DEFAULT 'Pending' CHECK (`status` IN ('Active', 'Pending', 'Inactive')),
  `activation_dt` DATE DEFAULT NULL,
  `create_dt` DATE DEFAULT NULL,
  PRIMARY KEY (`user_id`)
);

CREATE TABLE `acount_info` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` CHAR(36) NOT NULL UNIQUE,
  `icon_file_name` VARCHAR(100) NOT NULL,
  `description`    VARCHAR(1000) NOT NULL,
  `doctor_specialization` varchar(100),
  `office_no` varchar(20),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);


DELIMITER //

CREATE TRIGGER before_insert_user
BEFORE INSERT ON `user`
FOR EACH ROW
BEGIN
  IF NEW.user_id IS NULL THEN
    SET NEW.user_id = UUID();
  END IF;
END;
//

DELIMITER ;

CREATE TABLE verification_token (
  `token_id` CHAR(36) NOT NULL DEFAULT (UUID()),
  `user_id` CHAR(36) NOT NULL,
  `expiration_dt` DATE DEFAULT NULL,
  PRIMARY KEY (token_id)
);


CREATE TABLE `notice_details` (
  `notice_id` BIGINT NOT NULL AUTO_INCREMENT,
  `notice_summary` varchar(200) NOT NULL,
  `notice_details` varchar(500) NOT NULL,
  `notic_beg_dt` date NOT NULL,
  `notic_end_dt` date DEFAULT NULL,
  `create_dt` date DEFAULT NULL,
  `update_dt` date DEFAULT NULL,
  PRIMARY KEY (`notice_id`)
);


CREATE TABLE `contact_messages` (
  `contact_id` BIGINT NOT NULL AUTO_INCREMENT,
  `contact_name` varchar(50) NOT NULL,
  `contact_email` varchar(100) NOT NULL,
  `subject` varchar(500) NOT NULL,
  `message` varchar(2000) NOT NULL,
  `create_dt` date DEFAULT NULL,
  PRIMARY KEY (`contact_id`)
);




CREATE TABLE `drug_manufacturers` (
  `manufacturer_id` BIGINT NOT NULL AUTO_INCREMENT,
  `manufacturer` VARCHAR(100) NOT NULL,
  `manufacturer_desc` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`manufacturer_id`)
);

CREATE TABLE `drugs` (
  `drug_id` BIGINT NOT NULL AUTO_INCREMENT,
  `drug_market_name` VARCHAR(100) NOT NULL,
  `drug_desc` VARCHAR(1000) NOT NULL,
  `delivery_method` VARCHAR(100) NOT NULL DEFAULT 'Oral',
  `strength` VARCHAR(100) NOT NULL,
  `price` BIGINT,
  `active_ingredients` VARCHAR(1000) NOT NULL,
  `manufacturer_id` BIGINT NOT NULL,
  PRIMARY KEY (`drug_id`),
  FOREIGN KEY (`manufacturer_id`) REFERENCES `drug_manufacturers` (`manufacturer_id`) ON DELETE CASCADE
);


CREATE TABLE `drug_instance` (
  `drug_instance_id` BIGINT NOT NULL AUTO_INCREMENT,
  `drug_id` BIGINT NOT NULL,
  `create_dt` date DEFAULT NULL,
  `expiration_dt` date DEFAULT NULL,
  `status` VARCHAR(10) NOT NULL CHECK (status IN ('Available', 'Unavailable', 'Pending')),
  PRIMARY KEY (`drug_instance_id`),
  FOREIGN KEY (`drug_id`) REFERENCES `drugs` (`drug_id`) ON DELETE CASCADE
);

CREATE TABLE `prescriptions` (
  `prescription_id` BIGINT NOT NULL AUTO_INCREMENT,
  `prescriptions_summary` varchar(200) NOT NULL,
  `doctor_id` CHAR(36) NOT NULL,
  `patient_id` CHAR(36) NOT NULL,
  `create_dt` date DEFAULT NULL,
  `expiration_dt` date DEFAULT NULL,
  `fulfillement_dt` date DEFAULT NULL,
  `status` VARCHAR(10) NOT NULL CHECK (status IN ('Pending', 'Expired', 'Fulfilled')) DEFAULT 'Pending',
  PRIMARY KEY (`prescription_id`),
  FOREIGN KEY (`doctor_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  FOREIGN KEY (`patient_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `prescription_drugs` (
  `prescription_id` BIGINT NOT NULL,
  `drug_id` BIGINT NOT NULL,
  PRIMARY KEY (`prescription_id`, `drug_id`),
  FOREIGN KEY (`prescription_id`) REFERENCES `prescriptions` (`prescription_id`) ON DELETE CASCADE,
  FOREIGN KEY (`drug_id`) REFERENCES `drugs` (`drug_id`) ON DELETE CASCADE
);



CREATE TABLE `authorities` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` CHAR(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);



CREATE TABLE `doctor_info` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id`CHAR(36) NOT NULL,
  `doctor_specialization` varchar(100) NOT NULL,
  `office_no` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_id_fk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);



CREATE TABLE `doctor_patient` (
  `doctor_id` CHAR(36) NOT NULL,
  `patient_id` CHAR(36) NOT NULL,
  PRIMARY KEY (`doctor_id`, `patient_id`),
  CONSTRAINT `fk_doctor_number` FOREIGN KEY (`doctor_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_patient_id` FOREIGN KEY (`patient_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);


CREATE TABLE `time_slot` (
  `slot_id` BIGINT NOT NULL AUTO_INCREMENT,
  `start_time` TIME NOT NULL,
  PRIMARY KEY (`slot_id`)
);

CREATE TABLE `doctor_schedule` (
  `schedule_id` BIGINT NOT NULL AUTO_INCREMENT,
  `doctor_id` CHAR(36) NOT NULL,
  `day_of_week` ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY') NOT NULL,
  `slot_id` BIGINT NOT NULL,
  `available` BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`schedule_id`),
  FOREIGN KEY (`doctor_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  FOREIGN KEY (`slot_id`) REFERENCES `time_slot` (`slot_id`) ON DELETE CASCADE
);


CREATE TABLE `appointments` (
  `appointments_id` BIGINT NOT NULL AUTO_INCREMENT,
  `appointments_date` date NOT NULL ,
  `schedule_id` BIGINT NOT NULL ,
  `doctor_id` CHAR(36) NOT NULL,
  `patient_id` CHAR(36) DEFAULT NULL,
  `appointments_description` varchar(100) DEFAULT NULL,
  `create_dt` date DEFAULT NULL,
  PRIMARY KEY (`appointments_id`),
  FOREIGN KEY (`schedule_id`) REFERENCES `doctor_schedule` (`schedule_id`) ON DELETE CASCADE,
  FOREIGN KEY (`doctor_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  FOREIGN KEY (`patient_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);

DELIMITER //

CREATE TRIGGER create_doctor_schedule 
AFTER INSERT ON `user` 
FOR EACH ROW 
BEGIN
    DECLARE done BIGINT DEFAULT FALSE;
    DECLARE cur_time_slot_id INT;
    DECLARE day VARCHAR(10);
    DECLARE time_slot_time TIME;
  
    -- Declare cursor for selecting all rows from time_slots
    DECLARE cur CURSOR FOR SELECT slot_id, start_time FROM time_slot;
    
    -- Declare continue handler to exit loop when no more rows are found
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    IF NEW.role = 'Doctor' THEN
        -- Open cursor
        OPEN cur;
        
        -- Loop through all rows
        read_loop: LOOP
            -- Fetch row from cursor into variables
            FETCH cur INTO cur_time_slot_id, time_slot_time;
            
            -- Check if no more rows are found, then exit loop
            IF done THEN
                LEAVE read_loop;
            END IF;

            -- Set available to TRUE if start_time is '08:00:00' or '08:30:00'
            IF time_slot_time IN ('08:00:00', '08:30:00', '09:00:00', '09:30:00', '10:00:00', '10:30:00') THEN
                INSERT INTO doctor_schedule (doctor_id, day_of_week, slot_id, available)
                VALUES 
                    (NEW.user_id, 'MONDAY', cur_time_slot_id, TRUE),
                    (NEW.user_id, 'TUESDAY', cur_time_slot_id, TRUE),
                    (NEW.user_id, 'WEDNESDAY', cur_time_slot_id, TRUE),
                    (NEW.user_id, 'THURSDAY', cur_time_slot_id, TRUE),
                    (NEW.user_id, 'FRIDAY', cur_time_slot_id, TRUE),
                    (NEW.user_id, 'SATURDAY', cur_time_slot_id, TRUE),
                    (NEW.user_id, 'SUNDAY', cur_time_slot_id, TRUE);
            ELSE
                INSERT INTO doctor_schedule (doctor_id, day_of_week, slot_id, available)
                VALUES 
                    (NEW.user_id, 'MONDAY', cur_time_slot_id, FALSE),
                    (NEW.user_id, 'TUESDAY', cur_time_slot_id, FALSE),
                    (NEW.user_id, 'WEDNESDAY', cur_time_slot_id, FALSE),
                    (NEW.user_id, 'THURSDAY', cur_time_slot_id, FALSE),
                    (NEW.user_id, 'FRIDAY', cur_time_slot_id, FALSE),
                    (NEW.user_id, 'SATURDAY', cur_time_slot_id, FALSE),
                    (NEW.user_id, 'SUNDAY', cur_time_slot_id, FALSE);
            END IF;
        END LOOP;
        
        -- Close cursor
        CLOSE cur;
    END IF;
END;
//

DELIMITER ;


DELIMITER //

CREATE TRIGGER create_user_authorities
AFTER INSERT ON `user` 
FOR EACH ROW 
BEGIN
    IF NEW.role = 'Admin' THEN
        INSERT INTO `authorities` (`user_id`, `name`)
        VALUES (NEW.user_id, 'ROLE_USER'), (NEW.user_id, 'ROLE_ADMIN');
    END IF;

    IF NEW.role = 'User' THEN
        INSERT INTO `authorities` (`user_id`, `name`)
        VALUES (NEW.user_id, 'ROLE_USER');
    END IF;

    IF NEW.role = 'Doctor' THEN
        INSERT INTO `authorities` (`user_id`, `name`)
        VALUES (NEW.user_id, 'ROLE_USER'), (NEW.user_id, 'ROLE_DOCTOR');
    END IF;

    IF NEW.role = 'Patient' THEN
        INSERT INTO `authorities` (`user_id`, `name`)
        VALUES (NEW.user_id, 'ROLE_USER'), (NEW.user_id, 'ROLE_PATIENT');
    END IF;

    IF NEW.role = 'Pharmacist' THEN 
        INSERT INTO `authorities` (`user_id`, `name`)
        VALUES (NEW.user_id, 'ROLE_USER'), (NEW.user_id, 'ROLE_PHARMACIST');
    END IF;

    IF NEW.role = 'Secretary' THEN
        INSERT INTO `authorities` (`user_id`, `name`)
        VALUES (NEW.user_id, 'ROLE_USER'), (NEW.user_id, 'ROLE_SECRETARY');
    END IF;

    IF NEW.role = 'Nurse' THEN
        INSERT INTO `authorities` (`user_id`, `name`)
        VALUES (NEW.user_id, 'ROLE_USER'), (NEW.user_id, 'ROLE_NURSE');
    END IF;

    IF NEW.role = 'Technician' THEN
        INSERT INTO `authorities` (`user_id`, `name`)
        VALUES (NEW.user_id, 'ROLE_USER'), (NEW.user_id, 'ROLE_TECHNICIAN');
    END IF;
END;
//

DELIMITER ;



INSERT INTO time_slot ( start_time)
VALUES 
('08:00:00'),
('08:30:00'),
('09:00:00'),
('09:30:00'),
('10:00:00'),
('10:30:00'),
('11:00:00'),
('11:30:00'),
('12:00:00'),
('12:30:00'),
('13:00:00'),
('13:30:00'),
('14:00:00'),
('14:30:00'),
('15:00:00'),
('15:30:00'),
('16:00:00'),
('16:30:00'),
('17:00:00'),
('17:30:00'),
('18:00:00'),
('18:30:00'),
('19:00:00'),
('19:30:00'),
('20:00:00'),
('20:30:00'),
('21:00:00'),
('21:30:00');

-- Insert Doctors $2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2 

INSERT INTO `user` (
    `first_name`, `last_name`, `email`, `mobile_number`, `password`, `role`, `gender`, `create_dt`
) VALUES
    ('Admin', 'Admin', 'Admin@Admin.com', '9876548337', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Admin', 'Male', CURRENT_DATE),
    ('Happy', 'Happy', 'happy@example.com', '9876548327', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Admin', 'Male', CURRENT_DATE),
    ('Joyful', 'Joyful', 'joyful@example.com', '98712348222', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Admin', 'Male', CURRENT_DATE),
    ('Amber', 'Volakis', 'AmberVolakis@gmail.com', '5558339324', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Doctor', 'Female', CURRENT_DATE),
    ('Helen', 'Moure', 'HelenMoure@gmail.com', '5558339049', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Doctor', 'Female', CURRENT_DATE),
    ('Jessica', 'Adams', 'JessicaAdams@gmail.com', '5558339977', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Doctor', 'Female', CURRENT_DATE),
    ('Allison', 'Cameron', 'AllisonCameron@gmail.com', '5558339895', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Doctor', 'Female', CURRENT_DATE),
    ('Remy', 'Hadley', 'RemyHadley@gmail.com', '5558339027', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Doctor', 'Female', CURRENT_DATE),
    ('Robert', 'Chase', 'RobertChase@gmail.com', '5558239526', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Doctor', 'Male', CURRENT_DATE),
    ('James', 'Wilson', 'JamesWilson@gmail.com', '5558339124', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Doctor', 'Male', CURRENT_DATE),
    ('Eric', 'Foreman', 'EricForeman@gmail.com', '5558339625', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Doctor', 'Male', CURRENT_DATE),
    ('Marty', 'Kaufman', 'MartyKaufman@gmail.com', '5558339738', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Doctor', 'Male', CURRENT_DATE),
    ('Taub', 'Taub', 'ChrisTaub@gmail.com', '5558339412', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Doctor', 'Male', CURRENT_DATE);


INSERT INTO `user` (
    `first_name`, `last_name`, `email`, `mobile_number`, `password`, `role`, `gender`, `create_dt`
) VALUES
    ('Bradley', 'Jackson', 'BradleyJackson@gmail.com', '5558738653', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Pharmacist', 'Male', CURRENT_DATE);

insert into user (first_name, last_name, email, mobile_number, gender, password,  role , create_dt ) values 
    ('Minda', 'Rickersy', 'mrickersy0@answers.com', '147 184 8025', 'Female', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Justino', 'Mingardi', 'jmingardi1@meetup.com', '909 759 3693', 'Male', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Adamo', 'Cawley', 'acawley2@abc.net.au', '489 729 0879', 'Male', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Theodosia', 'Meadway', 'tmeadway3@bloglines.com', '347 614 9080', 'Female', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Romona', 'Oyley', 'royley4@engadget.com', '925 478 2730', 'Female', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Stanfield', 'Dusting', 'sdusting5@merriam-webster.com', '103 119 8819', 'Male', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Tadeas', 'Colbrun', 'tcolbrun6@hugedomains.com', '273 767 3373', 'Male', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Berenice', 'Randals', 'brandals7@deliciousdays.com', '432 196 9357', 'Female', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Saundra', 'Andre', 'sandre8@hugedomains.com', '162 217 4555', 'Male', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Kahaleel', 'Widdecombe', 'kwiddecombe9@clickbank.net', '742 542 8958', 'Male', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Barris', 'Pomeroy', 'bpomeroya@ebay.co.uk', '518 299 9820', 'Male', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Minetta', 'Birks', 'mbirksb@fc2.com', '572 533 9266', 'Female', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Ange', 'Norheny', 'anorhenyc@home.pl', '137 248 2085', 'Male', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Deerdre', 'Fishbourne', 'dfishbourned@vistaprint.com', '683 612 3623', 'Female', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Bondy', 'Anyene', 'banyenee@google.co.jp', '952 163 8348', 'Male', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Felecia', 'Reicherz', 'freicherzf@amazon.co.uk', '726 715 8397', 'Female', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Mervin', 'Louder', 'mlouderg@ucoz.ru', '199 292 3432', 'Male', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Colas', 'Spuner', 'cspunerh@list-manage.com', '238 287 5816', 'Male', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Perry', 'Torregiani', 'ptorregianii@arstechnica.com', '479 164 1768', 'Female', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE),
    ('Matti', 'Mattinson', 'mmattinsonj@hexun.com', '629 728 3828', 'Female', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'Patient',  CURRENT_DATE);

SET @doctor1 = (SELECT `user_id` FROM `user` WHERE `email` = 'AmberVolakis@gmail.com' );
SET @doctor2 = (SELECT `user_id` FROM `user` WHERE `email` = 'HelenMoure@gmail.com' );
SET @doctor3 = (SELECT `user_id` FROM `user` WHERE `email` = 'JessicaAdams@gmail.com' );
SET @doctor4 = (SELECT `user_id` FROM `user` WHERE `email` = 'AllisonCameron@gmail.com' );
SET @doctor5 = (SELECT `user_id` FROM `user` WHERE `email` = 'RemyHadley@gmail.com' );
SET @doctor6 = (SELECT `user_id` FROM `user` WHERE `email` = 'RobertChase@gmail.com' );
SET @doctor7 = (SELECT `user_id` FROM `user` WHERE `email` = 'JamesWilson@gmail.com' );
SET @doctor8 = (SELECT `user_id` FROM `user` WHERE `email` = 'EricForeman@gmail.com' );
SET @doctor9 = (SELECT `user_id` FROM `user` WHERE `email` = 'MartyKaufman@gmail.com' );
SET @doctor10 = (SELECT `user_id` FROM `user` WHERE `email` = 'ChrisTaub@gmail.com' );


INSERT INTO `acount_info` (`user_id`, `icon_file_name`, `description`, `doctor_specialization`, `office_no`) VALUES
(
    @doctor1,
    'Amber_Volakis.png',
    'Expert in diagnosing and treating imaging and radiological conditions.',
    'Radiology',
    '101'
),
(
    @doctor2,
    'default.png',
    'Specializes in diagnosing and treating disorders of the nervous system.',
    'Neurologist',
    '102'
),
(
    @doctor3,
    'House_Thirteen.png',
    'Expert in diagnosing and treating heart-related conditions.',
    'Cardiologist',
    '103'
),
(
    @doctor4,
    'Allison_Cameron.png',
    'Focuses on the diagnosis and treatment of immune system disorders.',
    'Immunologist',
    '201'
),
(
    @doctor5,
    'House_Thirteen.png',
    'Provides comprehensive care for adult patients, focusing on internal medicine.',
    'Internist',
    '202'
),
(
    @doctor6,
    'Robert_Chase.png',
    'Specializes in performing surgical procedures to treat various conditions.',
    'Surgeon',
    '203'
),
(
    @doctor7,
    'James_Wilson.png',
    'Expert in diagnosing and treating cancer and providing supportive care.',
    'Oncologist',
    '301'
),
(
    @doctor8,
    'Eric_Foreman.png',
    'Responsible for overseeing the hospital operations and medical staff.',
    'Dean of Medicine & Chief Hospital Administrator',
    '302'
),
(
    @doctor9,
    'default.png',
    'Specializes in the medical care of infants, children, and adolescents.',
    'Pediatrist',
    '303'
),
(
    @doctor10,
    'default.png',
    'Expert in surgical procedures related to the reconstruction of body parts.',
    'Plastic Surgeon',
    '401'
);




INSERT INTO `notice_details` (`notice_summary`, `notice_details`, `notic_beg_dt`, `notic_end_dt`, `create_dt`, `update_dt`)
VALUES ('New Hospital Service: Telemedicine Consultations', 
        'We are excited to announce the launch of our new telemedicine service! Patients can now schedule remote consultations with our healthcare providers from the comfort of their homes.', 
        CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);

INSERT INTO `notice_details` (`notice_summary`, `notice_details`, `notic_beg_dt`, `notic_end_dt`, `create_dt`, `update_dt`)
VALUES ('COVID-19 Vaccination Drive', 
        'In response to the ongoing pandemic, we are organizing a vaccination drive for all eligible patients and staff members. Please visit our hospital to get vaccinated and help protect our community.', 
        CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);

INSERT INTO `notice_details` (`notice_summary`, `notice_details`, `notic_beg_dt`, `notic_end_dt`, `create_dt`, `update_dt`)
VALUES ('New Hospital Facility: MRI Center Grand Opening', 
        'We are pleased to announce the grand opening of our state-of-the-art MRI center! This new facility will enhance our diagnostic capabilities and provide better care for our patients.', 
        CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);

INSERT INTO `notice_details` (`notice_summary`, `notice_details`, `notic_beg_dt`, `notic_end_dt`, `create_dt`, `update_dt`)
VALUES ('Upcoming Surgery Schedule Changes', 
        'Please note that there will be changes to the surgery schedule next week due to maintenance activities in our operating rooms. Patients with affected appointments will be contacted by our staff.', 
        CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);

INSERT INTO `notice_details` (`notice_summary`, `notice_details`, `notic_beg_dt`, `notic_end_dt`, `create_dt`, `update_dt`)
VALUES ('Health Insurance Policy Updates', 
        'We have updated our health insurance policies to better serve our patients. Please visit our website or contact our billing department for more information on coverage and benefits.', 
        CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);



INSERT INTO `drug_manufacturers` ( `manufacturer`, `manufacturer_desc`)
VALUES ('Bayer AG','The Bayer AG ( commonly pronounced /ˈbeɪər/ German: [ˈbaɪɐ]) is a German multinational pharmaceutical and biotechnology company and is one of the largest pharmaceutical companies and biomedical companies in the world.');

INSERT INTO `drugs` (`drug_market_name`, `drug_desc`, `delivery_method`, `strength`, `price`, `active_ingredients`, `manufacturer_id`)
VALUES 
('betaferon', 'Betaferon, also known by its generic name interferon beta-1b, is a medication primarily used in the treatment of multiple sclerosis (MS).', 'Injectable (Subcutaneous)', '0.3 mg', 100, 'Interferon beta 1b', 1),
('Humira', 'Used to treat autoimmune diseases like rheumatoid arthritis, psoriasis, Crohn\'s disease, and ulcerative colitis.', 'Injectable (Subcutaneous)', '10 mg', 200, 'Adalimumab', 1),
('Revlimid', 'Primarily used in the treatment of multiple myeloma and myelodysplastic syndromes.', 'Oral (Capsules)', '5 mg', 150, 'Lenalidomide', 1),
('Keytruda', 'An immunotherapy drug used in the treatment of various cancers, including melanoma, lung cancer, and head and neck cancer.', 'Injectable (Intravenous)', '25 mg/mL', 500, 'Pembrolizumab', 1),
('Eliquis', 'An anticoagulant used to prevent blood clots and strokes in patients with atrial fibrillation, deep vein thrombosis (DVT), or pulmonary embolism (PE).', 'Oral (Tablets)', '2.5 mg', 300, 'Apixaban', 1),
('Opdivo', 'Another immunotherapy drug used in the treatment of various cancers, including melanoma, lung cancer, and kidney cancer.', 'Injectable (Intravenous)', '10 mg/mL', 400, 'Nivolumab', 1),
('Imbruvica', 'Used in the treatment of certain cancers, including mantle cell lymphoma, chronic lymphocytic leukemia (CLL), and Waldenstrom\'s macroglobulinemia.', 'Oral (Capsules)', '70 mg', 250, 'Ibrutinib', 1),
('Herceptin', 'Primarily used in the treatment of HER2-positive breast cancer.', 'Injectable (Intravenous)', '150 mg', 600, 'Trastuzumab', 1),
('Avastin', 'Used to treat various cancers, including colorectal cancer, lung cancer, and kidney cancer.', 'Injectable (Intravenous)', '100 mg', 550, 'Bevacizumab', 1),
('Enbrel', 'Used to treat autoimmune diseases like rheumatoid arthritis, psoriasis, psoriatic arthritis, and ankylosing spondylitis.', 'Injectable (Subcutaneous)', '25 mg', 250, 'Etanercept', 1);


INSERT INTO `drugs` ( `drug_market_name`, `drug_desc`,`strength`,`price`,`active_ingredients`,`manufacturer_id`)
VALUES ('Xarelto','Rivaroxaban, sold under the brand name Xarelto, is an anticoagulant medication (blood thinner) used to treat and prevent blood clots.Specifically it is used to treat deep vein thrombosis and pulmonary emboli and prevent blood clots in atrial fibrillation and following hip or knee surgery. It is taken by mouth.',20,65,'Rivaroxaban',1);


 INSERT INTO `drug_instance` (  `drug_id`,`create_dt`,`expiration_dt`,`status`)
VALUES (1,'2024-02-20','2025-02-20','Available'),
 (1,'2024-02-20','2025-02-20','Available'),
 (1,'2024-02-20','2025-02-20','Available'),
 (1,'2024-02-20','2025-02-20','Available'),
 (2,'2024-02-20','2025-02-20','Available'),
 (2,'2024-02-20','2025-02-20','Available'),
 (2,'2024-02-20','2025-02-20','Available'),
 (3,'2024-02-20','2025-02-20','Available'),
 (3,'2024-02-20','2025-02-20','Available'),
 (3,'2024-02-20','2025-02-20','Available'),
 (4,'2024-02-20','2025-02-20','Available'),
 (4,'2024-02-20','2025-02-20','Available'),
 (4,'2024-02-20','2025-02-20','Available'),
 (5,'2024-02-20','2025-02-20','Available'),
 (5,'2024-02-20','2025-02-20','Available'),
 (5,'2024-02-20','2025-02-20','Available'),
 (6,'2024-02-20','2025-02-20','Available'),
 (6,'2024-02-20','2025-02-20','Available'),
 (6,'2024-02-20','2025-02-20','Available'),
 (7,'2024-02-20','2025-02-20','Available'),
 (7,'2024-02-20','2025-02-20','Available'),
 (7,'2024-02-20','2025-02-20','Available'),
 (8,'2024-02-20','2025-02-20','Available'),
 (8,'2024-02-20','2025-02-20','Available'),
 (8,'2024-02-20','2025-02-20','Available'),
 (9,'2024-02-20','2025-02-20','Available'),
 (9,'2024-02-20','2025-02-20','Available'),
 (9,'2024-02-20','2025-02-20','Available'),
 (10,'2024-02-20','2025-02-20','Available'),
 (10,'2024-02-20','2025-02-20','Available'),
 (10,'2024-02-20','2025-02-20','Available'),
 (10,'2024-02-20','2025-02-20','Available'),
 (1,'2024-02-20','2025-02-20','Available'),
 (1,'2024-02-20','2025-02-20','Available'),
 (1,'2024-02-20','2025-02-20','Available'),
 (1,'2024-02-20','2025-02-20','Available');


SET @appointments_date_from = '2024-04-12';
SET @appointments_date_to = '2025-04-12';
SET @doctor_id = NULL;
SET @doctor_specialization = NULL;
SET @start_time = NULL;
SET @day_of_week = NULL;
SELECT *
FROM appointments
JOIN doctor_schedule ON appointments.schedule_id = doctor_schedule.schedule_id
JOIN time_slot ON time_slot.slot_id = doctor_schedule.slot_id
JOIN doctor_info ON appointments.doctor_id = doctor_info.user_id
WHERE
    (@appointments_date_from IS NULL OR appointments_date BETWEEN @appointments_date_from AND @appointments_date_to) AND
    (@doctor_id IS NULL OR appointments.doctor_id = @doctor_id) AND
    (@patient_id IS NULL OR appointments.patient_id = @patient_id) AND
    (@doctor_specialization IS NULL OR doctor_info.doctor_specialization = @doctor_specialization) AND 
    (@start_time IS NULL OR time_slot.start_time = @start_time) AND 
    (@day_of_week IS NULL OR doctor_schedule.day_of_week = @day_of_week);




-- Insert doctor-patient relationships
-- Get the Doctor and Patient UUID's
SET @doctor1 = (SELECT `user_id` FROM `user` WHERE `email` = 'AmberVolakis@gmail.com' );
SET @doctor2 = (SELECT `user_id` FROM `user` WHERE `email` = 'HelenMoure@gmail.com' );
SET @doctor3 = (SELECT `user_id` FROM `user` WHERE `email` = 'JessicaAdams@gmail.com' );
SET @doctor4 = (SELECT `user_id` FROM `user` WHERE `email` = 'AllisonCameron@gmail.com' );
SET @doctor5 = (SELECT `user_id` FROM `user` WHERE `email` = 'RemyHadley@gmail.com' );
SET @doctor6 = (SELECT `user_id` FROM `user` WHERE `email` = 'RobertChase@gmail.com' );
SET @doctor7 = (SELECT `user_id` FROM `user` WHERE `email` = 'JamesWilson@gmail.com' );
SET @doctor8 = (SELECT `user_id` FROM `user` WHERE `email` = 'EricForeman@gmail.com' );
SET @doctor9 = (SELECT `user_id` FROM `user` WHERE `email` = 'MartyKaufman@gmail.com' );
SET @doctor10 = (SELECT `user_id` FROM `user` WHERE `email` = 'ChrisTaub@gmail.com' );

SET @patient1 = (SELECT `user_id` FROM `user` WHERE `email` =  'mrickersy0@answers.com');
SET @patient2 = (SELECT `user_id` FROM `user` WHERE `email` =  'jmingardi1@meetup.com');
SET @patient3 = (SELECT `user_id` FROM `user` WHERE `email` =  'acawley2@abc.net.au');
SET @patient4 = (SELECT `user_id` FROM `user` WHERE `email` =  'tmeadway3@bloglines.com');
SET @patient5 = (SELECT `user_id` FROM `user` WHERE `email` =  'royley4@engadget.com');
SET @patient6 = (SELECT `user_id` FROM `user` WHERE `email` =  'sdusting5@merriam-webster.com');
SET @patient7 = (SELECT `user_id` FROM `user` WHERE `email` =  'tcolbrun6@hugedomains.com');
SET @patient8 = (SELECT `user_id` FROM `user` WHERE `email` =  'brandals7@deliciousdays.com');
SET @patient9 = (SELECT `user_id` FROM `user` WHERE `email` =  'sandre8@hugedomains.com');
SET @patient10 = (SELECT `user_id` FROM `user` WHERE `email` =  'kwiddecombe9@clickbank.net');
SET @patient11 = (SELECT `user_id` FROM `user` WHERE `email` =  'bpomeroya@ebay.co.uk');
SET @patient12 = (SELECT `user_id` FROM `user` WHERE `email` =  'mbirksb@fc2.com');
SET @patient13 = (SELECT `user_id` FROM `user` WHERE `email` =  'anorhenyc@home.pl');
SET @patient14 = (SELECT `user_id` FROM `user` WHERE `email` =  'dfishbourned@vistaprint.com');
SET @patient15 = (SELECT `user_id` FROM `user` WHERE `email` =  'banyenee@google.co.jp');
SET @patient16 = (SELECT `user_id` FROM `user` WHERE `email` =  'freicherzf@amazon.co.uk');
SET @patient17 = (SELECT `user_id` FROM `user` WHERE `email` =  'mlouderg@ucoz.ru');
SET @patient18 = (SELECT `user_id` FROM `user` WHERE `email` =  'cspunerh@list-manage.com');
SET @patient19 = (SELECT `user_id` FROM `user` WHERE `email` =  'ptorregianii@arstechnica.com');
SET @patient20 = (SELECT `user_id` FROM `user` WHERE `email` =  'mmattinsonj@hexun.com');

-- Ensurering every patient is assigned to a doctor at least once
INSERT INTO doctor_patient (doctor_id, patient_id) VALUES
(@doctor1, @patient15),
(@doctor1, @patient2),
(@doctor1, @patient9),
(@doctor2, @patient4),
(@doctor2, @patient18),
(@doctor2, @patient11),
(@doctor3, @patient5),
(@doctor3, @patient7),
(@doctor3, @patient3),
(@doctor4, @patient16),
(@doctor4, @patient8),
(@doctor4, @patient10),
(@doctor5, @patient1),
(@doctor5, @patient12),
(@doctor5, @patient19),
(@doctor6, @patient14),
(@doctor6, @patient20),
(@doctor7, @patient6),
(@doctor7, @patient13),
(@doctor7, @patient17);

-- Randomly assign additional patients to each doctor, avoiding duplicates
INSERT INTO doctor_patient (doctor_id, patient_id)
SELECT doctor_id, patient_id
FROM (
    SELECT @doctor1 AS doctor_id, @patient1 AS patient_id UNION ALL
    SELECT @doctor1, @patient2 UNION ALL
    SELECT @doctor1, @patient3 UNION ALL
    SELECT @doctor1, @patient4 UNION ALL
    SELECT @doctor1, @patient5 UNION ALL
    SELECT @doctor1, @patient6 UNION ALL
    SELECT @doctor2, @patient1 UNION ALL
    SELECT @doctor2, @patient2 UNION ALL
    SELECT @doctor2, @patient3 UNION ALL
    SELECT @doctor2, @patient4 UNION ALL
    SELECT @doctor2, @patient5 UNION ALL
    SELECT @doctor2, @patient6 UNION ALL
    SELECT @doctor3, @patient1 UNION ALL
    SELECT @doctor3, @patient2 UNION ALL
    SELECT @doctor3, @patient3 UNION ALL
    SELECT @doctor3, @patient4 UNION ALL
    SELECT @doctor3, @patient5 UNION ALL
    SELECT @doctor3, @patient6 UNION ALL
    SELECT @doctor4, @patient1 UNION ALL
    SELECT @doctor4, @patient2 UNION ALL
    SELECT @doctor4, @patient3 UNION ALL
    SELECT @doctor4, @patient4 UNION ALL
    SELECT @doctor4, @patient5 UNION ALL
    SELECT @doctor4, @patient6 UNION ALL
    SELECT @doctor5, @patient1 UNION ALL
    SELECT @doctor5, @patient2 UNION ALL
    SELECT @doctor5, @patient3 UNION ALL
    SELECT @doctor5, @patient4 UNION ALL
    SELECT @doctor5, @patient5 UNION ALL
    SELECT @doctor5, @patient6 UNION ALL
    SELECT @doctor6, @patient1 UNION ALL
    SELECT @doctor6, @patient2 UNION ALL
    SELECT @doctor6, @patient3 UNION ALL
    SELECT @doctor6, @patient4 UNION ALL
    SELECT @doctor6, @patient5 UNION ALL
    SELECT @doctor6, @patient6 UNION ALL
    SELECT @doctor7, @patient1 UNION ALL
    SELECT @doctor7, @patient2 UNION ALL
    SELECT @doctor7, @patient3 UNION ALL
    SELECT @doctor7, @patient4 UNION ALL
    SELECT @doctor7, @patient5 UNION ALL
    SELECT @doctor7, @patient6 UNION ALL
    SELECT @doctor8, @patient1 UNION ALL
    SELECT @doctor8, @patient2 UNION ALL
    SELECT @doctor8, @patient3 UNION ALL
    SELECT @doctor8, @patient4 UNION ALL
    SELECT @doctor8, @patient5 UNION ALL
    SELECT @doctor8, @patient6 UNION ALL
    SELECT @doctor9, @patient1 UNION ALL
    SELECT @doctor9, @patient2 UNION ALL
    SELECT @doctor9, @patient3 UNION ALL
    SELECT @doctor9, @patient4 UNION ALL
    SELECT @doctor9, @patient5 UNION ALL
    SELECT @doctor9, @patient6 UNION ALL
    SELECT @doctor10, @patient1 UNION ALL
    SELECT @doctor10, @patient2 UNION ALL
    SELECT @doctor10, @patient3 UNION ALL
    SELECT @doctor10, @patient4 UNION ALL
    SELECT @doctor10, @patient5 UNION ALL
    SELECT @doctor10, @patient6
) AS random_assignments
WHERE (doctor_id, patient_id) NOT IN (SELECT doctor_id, patient_id FROM doctor_patient)
ORDER BY RAND()
LIMIT 20; 