mysql -u user_extraction -h 46.101.40.23 -p
password : password

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| db_CorpusECD       |
+--------------------+

mysql> use db_CorpusECD;
Database changed

mysql> show tables;
+------------------------+
| Tables_in_db_CorpusECD |
+------------------------+
| Documents              |
| Mots                   |
| Mots_Documents         |
+------------------------+

mysql> desc Documents;
+-------------+---------+------+-----+---------+----------------+
| Field       | Type    | Null | Key | Default | Extra          |
+-------------+---------+------+-----+---------+----------------+
| id          | int(11) | NO   | PRI | NULL    | auto_increment |
| avis        | text    | NO   |     | NULL    |                |
| polarite    | int(11) | NO   |     | -1      |                |
| nombre_mots | int(11) | YES  |     | 0       |                |
+-------------+---------+------+-----+---------+----------------+

mysql> select count(*) from Documents;
+----------+
| count(*) |
+----------+
|     2027 |
+----------+


mysql> desc Mots;
+-------------------+--------------+------+-----+---------+----------------+
| Field             | Type         | Null | Key | Default | Extra          |
+-------------------+--------------+------+-----+---------+----------------+
| id                | int(11)      | NO   | PRI | NULL    | auto_increment |
| value             | varchar(100) | YES  |     | NULL    |                |
| tf_max            | int(11)      | YES  |     | NULL    |                |
| tf_min            | int(11)      | YES  |     | NULL    |                |
| tf_cumule         | int(11)      | YES  |     | NULL    |                |
| nb_doc            | int(11)      | YES  |     | NULL    |                |
| idf               | double       | YES  |     | NULL    |                |
| tf_idfmax         | double       | YES  |     | NULL    |                |
| tf_idfmin         | double       | YES  |     | NULL    |                |
| polarite_negative | int(11)      | YES  |     | NULL    |                |
| polarite_positive | int(11)      | YES  |     | NULL    |                |
| tree_tagger_tag   | varchar(25)  | YES  |     | NULL    |                |
| df                | int(11)      | YES  |     | 0       |                |
+-------------------+--------------+------+-----+---------+----------------+
