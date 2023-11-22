# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|
  config.vm.box = "generic/debian12"
  config.vm.hostname = "docker.box"
  config.vm.network :private_network, ip: "192.168.56.10"
  config.vm.synced_folder ".", "/home/vagrant/workspace"

  config.vm.provision "shell", path: "vagrant/bootstrap.sh"

  #config.vm.provision "ansible" do |ansible|
  #  ansible.playbook = "playbook.yml"
  #end
end
