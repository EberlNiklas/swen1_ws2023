package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Package;
import at.technikum.apps.mtcg.repository.DatabasePackageRepository;
import at.technikum.apps.mtcg.repository.PackageRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class PackageService {

    private final PackageRepository packageRepository;

    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public void updateCoins(String username, int costs){
        packageRepository.updateCoins(username, costs);
    }

    public Package save(Package packageToBeCreated) {
        packageToBeCreated.setId(UUID.randomUUID().toString());
        return packageRepository.save(packageToBeCreated);
    }

    public int getCoinsFromUser(String username){
        return packageRepository.getCoinsFromUser(username);
    }

    public String getIdFromUser(String username){
        return packageRepository.getIdFromUser(username);
    }

    public String getIdFromPackage(){
        return packageRepository.getIdFromPackage();
    }

    public List<String> getCardsFromPackage(String package_id){
        return packageRepository.getCardsInPackage(package_id);
    }

    public void delete(String package_id){
        packageRepository.delete(package_id);
    }

}
