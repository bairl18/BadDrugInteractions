<?php include "../inc/dbinfo.inc"; ?>
<html>
<body>
<?php

   /* Usage: http://ec2-13-58-76-35.us-east-2.compute.amaonaws.com/test2.php?intid=[int_id] */
   $int_id = $_GET["intid"];

   /* Connect to MySQL and select the database. */
   $connection = mysqli_connect(DB_SERVER, DB_USERNAME, DB_PASSWORD);

   if (mysqli_connect_errno()) echo "Failed to connect to MySQL: " . mysqli_connect_error();

   $database = mysqli_select_db($connection, DB_DATABASE);

   /* Ensure that the multum_drug_name table exists */
   VerifyDrugName($connection, DB_DATABASE);

?>

<!-- Display table data. -->
<table border="1" cellpadding="2" cellspacing="2">
   <tr>
      <td>int_description</td>
   </tr>

<?php

   $result = mysqli_query($connection, "SELECT int_description FROM int_interaction_description WHERE int_id = " . $int_id );

   while($query_data = mysqli_fetch_row($result)) {
      echo "<tr>";
      echo "<td>", $query_data[0], "</td>";
      echo "</tr>";
   }
?>

</table>

<!-- Clean up. -->
<?php

   mysqli_free_result($result);
   mysqli_close($connection);

?>

</body>
</html>

<?php

/* Check whether multum_drug_name  exists. If not, write error message. */
function VerifyDrugName($connection, $dbName) {
   if(!TableExists("multum_drug_name", $connection, $dbName)) {

      echo "Table does not exist";
   }
}

/* Check for the existence of a table. */
function TableExists($tableName, $connection, $dbName) {
   $t = mysqli_real_escape_string($connection, $tableName);
   $d = mysqli_real_escape_string($connection, $dbName);

   $checktable = mysqli_query($connection,
      "SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_NAME = '$t' AND TABLE_SCHEMA = '$d'");

   if(mysqli_num_rows($checktable) > 0) return true;

   return false;
}
?>
