package DesignPattern.Strategy;

public enum TravelEnum {
   air("air","乘坐飞机"),train("train","乘坐火车");
   private String type;
   private String name;

   TravelEnum(String type, String name) {
      this.type = type;
      this.name = name;
   }

   public String getType() {
      return type;
   }

   public String getName() {
      if(type != null) {
         for(TravelEnum type : TravelEnum.values()) {
            if(type.getType().equals(type)) {
               return type.getName();
            }
         }
      }
      return null;
   }
}
