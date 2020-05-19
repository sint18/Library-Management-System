-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 19, 2020 at 08:31 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.2.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `myLibraryDB`
--

-- --------------------------------------------------------

--
-- Table structure for table `Admin`
--

CREATE TABLE `Admin` (
  `adminName` varchar(25) NOT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Admin`
--

INSERT INTO `Admin` (`adminName`, `username`, `password`) VALUES
('admin1', 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `author`
--

CREATE TABLE `author` (
  `authorID` int(5) NOT NULL,
  `authorName` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `author`
--

INSERT INTO `author` (`authorID`, `authorName`) VALUES
(10, 'Author1'),
(11, 'Author2'),
(12, 'Author3');

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `bookID` int(5) NOT NULL,
  `title` varchar(30) NOT NULL,
  `authorID` int(5) NOT NULL,
  `bookType` varchar(10) NOT NULL,
  `edition` varchar(20) NOT NULL,
  `location` varchar(20) NOT NULL,
  `isbn` varchar(20) DEFAULT NULL,
  `copies` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`bookID`, `title`, `authorID`, `bookType`, `edition`, `location`, `isbn`, `copies`) VALUES
(23, 'Title1', 10, 'Comic', '12th edition', '321u8', '901293782937', 4),
(24, 'Title2', 11, 'Horror', '1st edition', '321u9', '7348623873284', 4),
(25, 'Title3', 12, 'Comedy', '2nd edition', '321u10', '9812379812739', 4);

-- --------------------------------------------------------

--
-- Table structure for table `memberRecord`
--

CREATE TABLE `memberRecord` (
  `memberID` int(5) NOT NULL,
  `userID` int(5) NOT NULL,
  `dateOfMembership` date NOT NULL,
  `expireDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `memberRecord`
--

INSERT INTO `memberRecord` (`memberID`, `userID`, `dateOfMembership`, `expireDate`) VALUES
(23, 7, '2020-05-20', '2021-05-20');

-- --------------------------------------------------------

--
-- Table structure for table `status`
--

CREATE TABLE `status` (
  `statusID` int(5) NOT NULL,
  `memberID` int(5) NOT NULL,
  `bookID` int(5) NOT NULL,
  `borrowedDate` date NOT NULL,
  `dueDate` date DEFAULT NULL,
  `numberOfDays` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `status`
--

INSERT INTO `status` (`statusID`, `memberID`, `bookID`, `borrowedDate`, `dueDate`, `numberOfDays`) VALUES
(14, 23, 23, '2020-03-12', '2020-03-19', 7),
(15, 23, 24, '2020-05-19', NULL, 10),
(16, 23, 25, '2020-05-15', '2020-05-17', 2);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userID` int(5) NOT NULL,
  `fullname` varchar(25) NOT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(8) NOT NULL,
  `nrc` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userID`, `fullname`, `username`, `password`, `nrc`) VALUES
(7, 'User1', 'user1', 'user1', 'test123');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Admin`
--
ALTER TABLE `Admin`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `author`
--
ALTER TABLE `author`
  ADD PRIMARY KEY (`authorID`);

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`bookID`),
  ADD UNIQUE KEY `isbn` (`isbn`),
  ADD KEY `authorID` (`authorID`);

--
-- Indexes for table `memberRecord`
--
ALTER TABLE `memberRecord`
  ADD PRIMARY KEY (`memberID`),
  ADD UNIQUE KEY `userID` (`userID`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`statusID`),
  ADD KEY `memberID` (`memberID`),
  ADD KEY `bookID` (`bookID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userID`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `author`
--
ALTER TABLE `author`
  MODIFY `authorID` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `bookID` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `memberRecord`
--
ALTER TABLE `memberRecord`
  MODIFY `memberID` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `status`
--
ALTER TABLE `status`
  MODIFY `statusID` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userID` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
