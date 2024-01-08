package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Package;
import at.technikum.apps.mtcg.repository.DatabasePackageRepository;
import at.technikum.apps.mtcg.repository.PackageRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class PackageService {

    private final PackageRepository packageRepository;

    public PackageService() {
        this.packageRepository = new DatabasePackageRepository();
    }

    public Package update(Package oldPkg, Package newPkg){
        return packageRepository.update(oldPkg,newPkg);
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

}
