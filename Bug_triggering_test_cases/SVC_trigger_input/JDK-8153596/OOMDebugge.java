public void run(){
        try{
        while(true){
        list.add(new byte[50000]);
        }
        }catch(OutOfMemoryError err){
        Util.getLogger().info("Expected OOM in Debuggee");
        list.removeFirst();
        }

        Utils.sleep();
        }