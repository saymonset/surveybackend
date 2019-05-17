package com.service;

import com.tools.ToJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Map;

import static sun.plugin2.ipc.IPCFactory.stringToMap;

/**
 * Created by simon on 5/17/2019.
 */
@Service
public class DataFillService {
    Logger logger =  LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private ResourceLoader resourceLoader;
    @Inject
    private ToJson toJson;
    // CREATE DATA /////////////////////////////////////////////////////////////
    public void createAll() {
        createPositioning();
    }

    public void createPositioning() {
        Resource resource = resourceLoader.getResource("classpath:data/json/csvjson.json");
        Map<String, Object> map = toJson.stringToMap(toJson.resourceToString(resource));
        ArrayList data = null;// (ArrayList) map.get("DATA");

       /* GeographicDistribution nation = null;
        List<GeographicDistribution> nations = geographicDistributionRepository.findByRoot("1");

        if(nations.size() > 0){
            nation = nations.get(0);
        }

        int count = 0;
        for (Object distObject : data) {
            GeographicDistribution region = null;
            GeographicDistribution zone = null;
            GeographicDistribution branch = null;
            Map<String, Object> dist = (Map<String, Object>) distObject;

            if(nation == null){
                nation = new GeographicDistribution();
                nation.setName("NACIONAL");
                nation.setRoot("1");
                nation.setLevel("NATION");
                nation.setCode("N" + numberToStringMinimalThreeDigits(0));

                this.geographicDistributionRepository.save(nation);
                nations = null;
            }

            if (StringUtils.isNotBlank(String.valueOf(dist.get("GRUPO"))) && !String.valueOf(dist.get("GRUPO")).equalsIgnoreCase("TODOS")){

                region = geographicDistributionRepository.findByCode(String.valueOf(dist.get("ID_GRUPO")));

                if(region == null){ //Inserting new geo
                    region = new GeographicDistribution();
                }

                region.setName(String.valueOf(dist.get("GRUPO")));
                region.setRoot("");
                region.setLevel("REGION");
                region.setCode(String.valueOf(dist.get("ID_GRUPO")));
                region.setParent(nation.getId());

                this.geographicDistributionRepository.save(region);
            }

            if (StringUtils.isNotBlank(String.valueOf(dist.get("Matriz"))) && region != null ){

                zone = geographicDistributionRepository.findByCode(String.valueOf(dist.get("ID_Matriz")));

                if(zone == null){ //Inserting new geo
                    zone = new GeographicDistribution();
                }
                zone.setName(String.valueOf(dist.get("Matriz")));
                zone.setRoot("");
                zone.setLevel("ZONE");
                zone.setCode(String.valueOf(dist.get("ID_Matriz")));
                zone.setParent(region.getId());

                this.geographicDistributionRepository.save(zone);
            }
            String branchType = "";
            String branchName = "";
            String branchCode = "";
            if(!String.valueOf(dist.get("Distribuidora")).isEmpty()){
                branchName = String.valueOf(dist.get("Distribuidora"));
                branchCode = String.valueOf(dist.get("ID_Distribuidora"));
            }else if(!String.valueOf(dist.get("PV")).isEmpty()){
                branchName = String.valueOf(dist.get("PV"));
                branchCode = String.valueOf(dist.get("ID_PV"));
            }else if (!String.valueOf(dist.get("PV+")).isEmpty()){
                branchName = String.valueOf(dist.get("PV+"));
                branchCode = String.valueOf(dist.get("ID_PV+"));
            }else if(!String.valueOf(dist.get("Taller")).isEmpty()){
                branchName = String.valueOf(dist.get("Taller"));
                branchCode = String.valueOf(dist.get("ID_Taller"));
            }else if(!String.valueOf(dist.get("CC")).isEmpty()){
                branchName = String.valueOf(dist.get("CC"));
                branchCode = String.valueOf(dist.get("ID_CC"));
            }
            branchType = String.valueOf(dist.get("Tipo_Sucursal"));

            if(!branchName.isEmpty() && !branchCode.isEmpty() && region != null && zone != null){

                branch = geographicDistributionRepository.findByCode(branchCode);

                if(branch == null){ //Inserting new geo
                    branch = new GeographicDistribution();
                }
                branch.setName(branchName);
                branch.setRoot("");
                branch.setLevel("BRANCH");
                branch.setCode(branchCode);
                branch.setParent(zone.getId());
                branch.setBranchType(branchType);

                this.geographicDistributionRepository.save(branch);
            }

            if(nation != null && region != null){
                Set<GeographicDistribution> regions;
                regions = nation.getGeographicDistributions();
                if(regions != null){
                    nation.getGeographicDistributions().add(region);
                }else{
                    regions = new HashSet<>();
                    regions.add(region);
                    nation.setGeographicDistributions(regions);
                }
                this.geographicDistributionRepository.save(nation);
                regions = null;
            }
            if(region != null && zone != null){
                Set<GeographicDistribution> zones;
                zones = region.getGeographicDistributions();
                if(zones != null){
                    region.getGeographicDistributions().add(zone);
                }else{
                    zones = new HashSet<>();
                    zones.add(zone);
                    region.setGeographicDistributions(zones);
                }
                this.geographicDistributionRepository.save(region);
                zones = null;
            }
            if(zone != null && branch != null){
                Set<GeographicDistribution> branchs;
                branchs = zone.getGeographicDistributions();
                if(branchs != null){
                    zone.getGeographicDistributions().add(branch);
                }else{
                    branchs = new HashSet<>();
                    branchs.add(branch);
                    zone.setGeographicDistributions(branchs);
                }
                this.geographicDistributionRepository.save(zone);
                branchs = null;
            }
            count++;
            if(count % 500 == 0 && count > 0){
//            Call the garbage collector
                System.gc();
                System.runFinalization();
            }
        }*/
    }
}
