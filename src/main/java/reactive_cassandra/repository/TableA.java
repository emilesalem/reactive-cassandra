package reactive_cassandra.repository;

import org.javatuples.Pair;
import org.javatuples.Unit;

public class TableA {

    protected static String tableName = "table_a";

    public static class Queries {

        protected static String selectPartition(PartitionKey partitionKey) {
            return "SELECT * FROM " +
                    tableName +
                    " WHERE " +
                    PartitionKey.FIELD_0 +
                    " = '" +
                    partitionKey.getValue() +
                    "';";
        }

        protected static String selectRow(PrimaryKey primaryKey) {
            return "SELECT * FROM " +
                    tableName +
                    " WHERE " +
                    PrimaryKey.FIELD_0 +
                    " = '" +
                    primaryKey.getValue0() +
                    "'" +
                    " AND " +
                    PrimaryKey.FIELD_1 +
                    " = '" +
                    primaryKey.getValue1() +
                    "';";
        }
    }

    public static class PartitionKey {

        protected static String FIELD_0 = "field0";
        protected static Unit<String> value;

        public PartitionKey(String value) {
            this.value = new Unit(value);
        }

        public String getValue() {
            return this.value.getValue0();
        }
    }

    public static class PrimaryKey {

        protected static String FIELD_0 = PartitionKey.FIELD_0;
        protected static String FIELD_1 = "field1";
        protected static Pair<PartitionKey, String> value;

        public PrimaryKey(PartitionKey partitionKey, String value1) {
            this.value = new Pair(partitionKey, value1);
        }

        public String getValue0() {
            return this.value.getValue0().getValue();
        }

        public String getValue1() {
            return this.value.getValue1();
        }

        public static class Builder {

            PartitionKey value0;
            String value1;

            public Builder withValue0(PartitionKey value0) {
                this.value0 = value0;
                return this;
            }

            public Builder withValue1(String value1) {
                this.value1 = value1;
                return this;
            }

            public PrimaryKey build() {
                return new PrimaryKey(value0, value1);
            }
        }
    }
}
